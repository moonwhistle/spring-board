package com.board.comment.controller;

import com.board.comment.controller.dto.reponse.CommentResponse;
import com.board.comment.controller.dto.reponse.CommentResponses;
import com.board.comment.controller.dto.request.CommentRequest;
import com.board.comment.service.CommentService;
import com.board.global.resolver.annotation.Auth;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/articles/{articleId}/comments")
    public ResponseEntity<CommentResponse> createComment(@RequestBody CommentRequest request, @Auth Long memberId, @PathVariable Long articleId) {
        CommentResponse response = commentService.createComment(request, memberId, articleId);
        URI location = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(response.articleId())
                .toUri();

        return ResponseEntity.created(location).body(response);
    }

    @GetMapping("/articles/{articleId}/comments")
    public ResponseEntity<CommentResponses> showArticleComments(
            @PathVariable Long articleId,
            @RequestParam Long lastId,
            @RequestParam int size
    ) {
        return ResponseEntity.ok(commentService.showArticleComments(articleId, lastId, size));
    }

    @GetMapping("members/me/comments")
    public ResponseEntity<CommentResponses> showMemberComments(
            @Auth Long memberId,
            @RequestParam int page,
            @RequestParam int size
    ) {
        return ResponseEntity.ok(commentService.showMemberComments(memberId, page, size));
    }

    @PatchMapping("/comments/{commentId}")
    public ResponseEntity<CommentResponse> updateComment(@RequestBody CommentRequest request, @Auth Long memberId, @PathVariable Long commentId) {
        return ResponseEntity.ok(commentService.updateComment(request, memberId, commentId));
    }

    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<CommentResponse> deleteComment(@Auth Long memberId, @PathVariable Long commentId) {
        return ResponseEntity.ok(commentService.deleteComment(memberId, commentId));
    }
}
