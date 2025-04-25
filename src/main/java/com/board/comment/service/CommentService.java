package com.board.comment.service;

import com.board.comment.controller.dto.request.CommentRequest;
import com.board.comment.domain.Comment;
import com.board.comment.exception.CommentErrorCode;
import com.board.comment.exception.CommentException;
import com.board.comment.repository.CommentRepository;
import java.util.List;
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

    private static final String PAGE_SORT_DELIMITER = "id";
    private static final int NO_OFFSET_PAGING_PAGE = 0;

    private final CommentRepository commentRepository;

    public Comment createComment(String content, Long memberId, Long articleId) {
        Comment comment = new Comment(memberId, articleId, content);
        commentRepository.save(comment);

        return comment;
    }

    public Comment updateComment(CommentRequest request, Long memberId, Long commentId) {
        Comment comment = getComment(commentId);
        comment.validateAccessAboutComment(memberId);
        comment.update(request.content());

        return comment;
    }

    public Comment deleteComment(Long memberId, Long commentId) {
        Comment comment = getComment(commentId);
        comment.validateAccessAboutComment(memberId);
        commentRepository.delete(comment);

        return comment;
    }

    @Transactional(readOnly = true)
    public List<Comment> getArticleComments(Long articleId, Long lastId, int size) {
        Pageable commentPageable = PageRequest.of(NO_OFFSET_PAGING_PAGE, size, Sort.by(PAGE_SORT_DELIMITER).descending());
        return commentRepository.findByArticleIdAndIdLessThanOrderByIdDesc(articleId, lastId, commentPageable);
    }

    @Transactional(readOnly = true)
    public Page<Comment> getMemberComments(Long memberId, int page, int size) {
        Pageable commentPageable = PageRequest.of(page, size, Sort.by(PAGE_SORT_DELIMITER).descending());
        return commentRepository.findAllByMemberId(memberId, commentPageable);
    }

    @Transactional(readOnly = true)
    public Comment getComment(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentException(CommentErrorCode.NOT_FOUND_COMMENT));
    }
}
