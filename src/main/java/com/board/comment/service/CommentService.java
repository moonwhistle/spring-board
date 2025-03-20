package com.board.comment.service;

import com.board.comment.controller.dto.reponse.CommentResponse;
import com.board.comment.controller.dto.reponse.CommentResponses;
import com.board.comment.controller.dto.request.CommentRequest;
import com.board.comment.domain.Comment;
import com.board.comment.repository.CommentRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
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

    public CommentResponses showArticleComments(Long articleId) {
        List<CommentResponse> commentResponses = getArticleComments(articleId).stream()
                .map(comment -> new CommentResponse(
                        comment.getMemberId(),
                        comment.getArticleId(),
                        comment.getContent()
                ))
                .toList();

        return new CommentResponses(commentResponses);
    }

    @Transactional(readOnly = true)
    public List<Comment> getArticleComments(Long articleId) {
        return commentRepository.findAllByArticleId(articleId);
    }
}
