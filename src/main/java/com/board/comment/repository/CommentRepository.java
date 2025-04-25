package com.board.comment.repository;

import com.board.comment.domain.Comment;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByArticleIdAndIdLessThanOrderByIdDesc(Long articleId, Long lastId, Pageable pageable);

    Page<Comment> findAllByMemberId(Long memberId, Pageable pageable);
}
