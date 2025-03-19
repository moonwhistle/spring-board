package com.board.member.controller.member;

import com.board.global.resolver.annotation.Auth;
import com.board.member.controller.member.dto.reponse.MemberResponse;
import com.board.member.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/members")
    public ResponseEntity<MemberResponse> showMember(@Auth Long memberId) {
        return ResponseEntity.ok(memberService.getMember(memberId));
    }
}
