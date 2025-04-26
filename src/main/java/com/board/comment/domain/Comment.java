package com.board.comment.domain;

import com.board.article.domain.Article;
import com.board.comment.exception.CommentErrorCode;
import com.board.comment.exception.CommentException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long memberId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "articleId", nullable = false)
    private Article article;


    @Column(nullable = false)
    private String content;

    public Comment(Long memberId, Article article, String content) {
        this.memberId = memberId;
        this.article = article;
        this.content = content;
    }

    public void update(String content) {
        if (content != null) {
            this.content = content;
        }
    }

    public void validateAccessAboutComment(Long memberId) {
        if(!Objects.equals(this.memberId, memberId)) {
            throw new CommentException(CommentErrorCode.FORBIDDEN_ACCESS_COMMENT);
        }
    }
}
