package com.board.article.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.board.article.domain.Article;
import com.board.article.loader.ArticleTestDataLoader;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@DataJpaTest
@Import(ArticleTestDataLoader.class)
class ArticleRepositoryTest {

    @Autowired
    private ArticleRepository articleRepository;

    @Test
    @DisplayName("멤버 아이디에 해당하는 게시글을 가져온다.")
    void findArticleByMemberId() {
        // given
        Long memberId = 1L;

        // when
        List<Article> articles = articleRepository.findArticleByMemberId(memberId);

        // then
        assertThat(articles).hasSize(2);
        assertThat(articles).extracting(Article::getTitle)
                .containsExactly("신형만에 관하여", "신짱구");
    }
}
