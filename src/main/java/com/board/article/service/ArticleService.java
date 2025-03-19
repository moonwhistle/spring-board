package com.board.article.service;

import com.board.article.controller.dto.request.ArticleRequest;
import com.board.article.controller.dto.response.ArticleResponse;
import com.board.article.domain.Article;
import com.board.article.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
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
}
