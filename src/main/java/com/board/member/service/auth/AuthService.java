package com.board.member.service.auth;

import com.board.member.controller.auth.dto.request.SignUpRequest;
import com.board.member.controller.auth.dto.response.SignUpResponse;
import com.board.member.domain.auth.TokenProvider;
import com.board.member.domain.member.Member;
import com.board.member.repository.MemberRepository;
import com.board.member.service.auth.exception.ExistLoginIdException;
import com.board.member.service.auth.exception.ExistNickNameException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;
    private final TokenProvider tokenProvider;

    public SignUpResponse signUp(SignUpRequest request) {
        checkDuplicateLoginId(request.loginId());
        checkDuplicateNickName(request.memberNickName());
        Member member = new Member(request.memberName(), request.memberNickName(), request.loginId(), request.password());
        memberRepository.save(member);

        return new SignUpResponse(member.getId(), tokenProvider.create(member.getId()));
    }

    private void checkDuplicateLoginId(String loginId) {
        if(memberRepository.existsByMemberLoginId(loginId)) {
            throw new ExistLoginIdException();
        }
    }

    private void checkDuplicateNickName(String memberNickName) {
        if(memberRepository.existsByMemberNickName(memberNickName)) {
            throw new ExistNickNameException();
        }
    }
}
