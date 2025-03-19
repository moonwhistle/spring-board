package com.board.article.service;

import com.board.article.controller.dto.request.ArticleRequest;
import com.board.article.controller.dto.response.ArticleResponse;
import com.board.article.controller.dto.response.ArticleResponses;
import com.board.article.domain.Article;
import com.board.article.repository.ArticleRepository;
import com.board.article.service.exception.NotFoundArticleException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ArticleService {

    private final ArticleRepository articleRepository;

    public ArticleResponse createArticle(ArticleRequest request, Long memberId) {
        Article article = new Article(memberId, request.title(), request.content());
        articleRepository.save(article);

        return new ArticleResponse(
                article.getId(),
                article.getMemberId(),
                article.getTitle(),
                article.getContent()
        );
    }

    public ArticleResponses showAllArticles() {
        List<ArticleResponse> articleResponses = new ArrayList<>();
        List<Article> articles = getAllArticles();

        for (Article article : articles) {
            articleResponses.add(new ArticleResponse(
                    article.getId(),
                    article.getMemberId(),
                    article.getTitle(),
                    article.getContent()
            ));
        }

        return new ArticleResponses(articleResponses);
    }

    public ArticleResponse showArticle(Long articleId) {
        Article article = getArticle(articleId);

        return new ArticleResponse(
                article.getId(),
                article.getMemberId(),
                article.getTitle(),
                article.getContent()
        );
    }

    @Transactional(readOnly = true)
    public List<Article> getAllArticles() {
        return articleRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Article getArticle(Long articleId) {
        return articleRepository.findById(articleId)
                .orElseThrow(NotFoundArticleException::new);
    }
}
