package com.board.article.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.board.article.domain.Article;
import com.board.article.loader.ArticleTestDataLoader;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@DataJpaTest
@Import(ArticleTestDataLoader.class)
class ArticleRepositoryTest {

    @Autowired
    private ArticleRepository articleRepository;

    @Test
    @DisplayName("멤버 아이디에 해당하는 게시글을 가져온다.(offset-paging)")
    void findArticleByMemberId() {
        // given
        Pageable pageable = PageRequest.of(0, 10, Sort.by("id").descending());
        Long memberId = 1L;

        // when
        Page<Article> articles = articleRepository.findArticleByMemberId(memberId, pageable);

        // then
        assertThat(articles).hasSize(2);
        assertThat(articles).extracting(Article::getTitle)
                .containsExactly("신형만에 관하여", "신짱구");
    }
}
