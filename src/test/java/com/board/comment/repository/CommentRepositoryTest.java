package com.board.comment.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.board.comment.domain.Comment;
import com.board.comment.loader.CommentTestDataLoader;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@DataJpaTest
@Import(CommentTestDataLoader.class)
class CommentRepositoryTest {

    @Autowired
    private CommentRepository commentRepository;

    @Test
    @DisplayName("게시글 아이디에 해당하는 모든 댓글을 조회한다.")
    void findAllByArticleId() {
        // given
        Long articleId = 1L;

        // when
        List<Comment> comments = commentRepository.findAllByArticleId(articleId);

        // then
        assertThat(comments).hasSize(2)
                .extracting(Comment::getContent)
                .containsExactly("첫 번째 게시글 댓글 1", "첫 번째 게시글 댓글 2");
    }

    @Test
    @DisplayName("멤버 아이디에 해당하는 모든 댓글을 조회한다.")
    void findAllByMemberId() {
        // given
        Long memberId = 1L;

        // when
        List<Comment> comments = commentRepository.findAllByMemberId(memberId);

        // then
        assertThat(comments).hasSize(4)
                .extracting(Comment::getMemberId)
                .containsExactly(1L, 1L, 1L, 1L);
    }
}
