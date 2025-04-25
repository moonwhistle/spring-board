package com.board.comment.service;

import com.board.comment.controller.dto.reponse.CommentResponse;
import com.board.comment.controller.dto.reponse.CommentResponses;
import com.board.comment.controller.dto.request.CommentRequest;
import com.board.comment.domain.Comment;
import com.board.comment.exception.CommentErrorCode;
import com.board.comment.exception.CommentException;
import com.board.comment.repository.CommentRepository;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    public CommentResponses showMemberComments(Long memberId, int page, int size) {
        List<CommentResponse> commentResponses = getMemberComments(memberId, page, size).stream()
                .map(comment -> new CommentResponse(
                        comment.getMemberId(),
                        comment.getArticleId(),
                        comment.getContent()
                ))
                .toList();

        return new CommentResponses(commentResponses);
    }

    public CommentResponse updateComment(CommentRequest request, Long memberId, Long commentId) {
        Comment comment = getComment(commentId);
        validateAccessAboutComment(memberId, comment);
        comment.update(request.content());

        return new CommentResponse(
                comment.getMemberId(),
                comment.getArticleId(),
                comment.getContent()
        );
    }

    public CommentResponse deleteComment(Long memberId, Long commentId) {
        Comment comment = getComment(commentId);
        validateAccessAboutComment(memberId, comment);
        commentRepository.delete(comment);

        return new CommentResponse(
                comment.getMemberId(),
                comment.getArticleId(),
                comment.getContent()
        );
    }

    private void validateAccessAboutComment(Long memberId, Comment comment) {
        if (!Objects.equals(comment.getMemberId(), memberId)) {
            throw new CommentException(CommentErrorCode.FORBIDDEN_ACCESS_COMMENT);
        }
    }

    @Transactional(readOnly = true)
    public List<Comment> getArticleComments(Long articleId) {
        return commentRepository.findAllByArticleId(articleId);
    }

    @Transactional(readOnly = true)
    public Page<Comment> getMemberComments(Long memberId, int page, int size) {
        Pageable commentPageable = PageRequest.of(page, size, Sort.by("id").descending());
        return commentRepository.findAllByMemberId(memberId, commentPageable);
    }

    @Transactional(readOnly = true)
    public Comment getComment(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentException(CommentErrorCode.NOT_FOUND_COMMENT));
    }
}
