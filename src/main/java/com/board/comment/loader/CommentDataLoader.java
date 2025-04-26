package com.board.comment.loader;

import com.board.article.domain.Article;
import com.board.article.repository.ArticleRepository;
import com.board.comment.domain.Comment;
import com.board.comment.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentDataLoader implements CommandLineRunner {

    private final CommentRepository repository;
    private final ArticleRepository articleRepository;

    @Override
    public void run(String... args) throws Exception {
        Article article1 = articleRepository.save(new Article(1L, "제목", "내용"));
        Article article2 = articleRepository.save(new Article(1L, "제제목", "내내용"));
        repository.save(new Comment(1L, article1, "첫 번째 게시글 댓글 1"));
        repository.save(new Comment(1L, article1, "첫 번째 게시글 댓글 2"));
        repository.save(new Comment(1L, article2, "두 번째 게시글 댓글 1"));
        repository.save(new Comment(1L, article2, "두 번째 게시글 댓글 2"));
    }
}
