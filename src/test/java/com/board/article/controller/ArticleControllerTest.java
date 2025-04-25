package com.board.article.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.board.article.controller.dto.request.ArticleRequest;
import com.board.article.controller.dto.response.ArticleResponse;
import com.board.article.controller.dto.response.ArticleResponses;
import com.board.article.service.ArticleService;
import com.board.global.resolver.AuthArgumentResolver;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ArticleController.class)
class ArticleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ArticleService articleService;

    @MockBean
    private AuthArgumentResolver authArgumentResolver;

    private ArticleResponse articleResponse;
    private ArticleResponses articleResponses;

    @BeforeEach
    void setUp() throws Exception {
        given(authArgumentResolver.supportsParameter(any())).willReturn(true);
        given(authArgumentResolver.resolveArgument(any(), any(), any(), any())).willReturn(1L);
        articleResponse = new ArticleResponse(1L, 1L, "제목", "내용");
        articleResponses = new ArticleResponses(List.of(articleResponse));
    }

    @Test
    @DisplayName("게시글을 생성한다.")
    void createArticle() throws Exception {
        // given
        ArticleRequest request = new ArticleRequest("제목", "내용");
        given(articleService.createArticle(any(), any())).willReturn(articleResponse);

        // when & then
        mockMvc.perform(post("/articles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "http://localhost/articles/1"))
                .andExpect(jsonPath("$.articleId").value(1L));
    }

    @Test
    @DisplayName("모든 게시글을 조회한다.")
    void showAllArticles() throws Exception {
        // given
        given(articleService.showAllArticles()).willReturn(articleResponses);

        // when & then
        mockMvc.perform(get("/articles"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.articleResponses[0].articleId").value(1L));
    }

    @Test
    @DisplayName("게시글 하나를 조회한다.")
    void showArticle() throws Exception {
        // given
        given(articleService.showArticle(1L)).willReturn(articleResponse);

        // when & then
        mockMvc.perform(get("/articles/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.articleId").value(1L));
    }

    @Test
    @DisplayName("회원의 게시글을 조회한다.")
    void showMemberArticles() throws Exception {
        // given
        Long memberId = 1L;
        int page = 0;
        int size = 10;
        given(articleService.showMemberArticles(memberId, page, size)).willReturn(articleResponses);

        // when & then
        mockMvc.perform(get("/members/me/articles")
                        .param("page", String.valueOf(page))
                        .param("size", String.valueOf(size)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.articleResponses[0].articleId").value(1L));
    }

    @Test
    @DisplayName("게시글을 수정한다.")
    void updateArticle() throws Exception {
        // given
        ArticleRequest request = new ArticleRequest("수정된 제목", "수정된 내용");
        ArticleResponse response = new ArticleResponse(1L, 1L, "수정된 제목", "수정된 내용");
        given(articleService.updateArticle(any(), any(), any())).willReturn(response);

        // when & then
        mockMvc.perform(patch("/articles/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("수정된 제목"));
    }

    @Test
    @DisplayName("게시글을 삭제한다.")
    void deleteArticle() throws Exception {
        // given
        given(articleService.deleteArticle(1L, 1L)).willReturn(articleResponse);

        // when & then
        mockMvc.perform(delete("/articles/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.articleId").value(1L));
    }
}
