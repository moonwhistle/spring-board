package com.board.comment.service;

import com.board.comment.controller.dto.reponse.CommentResponse;
import com.board.comment.controller.dto.request.CommentRequest;
import com.board.comment.domain.Comment;
import com.board.comment.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    public CommentResponse createComment(CommentRequest request, Long memberId, Long articleId) {
        Comment comment = new Comment(memberId, articleId, request.content());
        commentRepository.save(comment);

        return new CommentResponse(
                comment.getMemberId(),
                comment.getArticleId(),
                comment.getContent()
        );
    }
}
