package com.board.member.service.auth;

import com.board.member.controller.auth.dto.request.LoginRequest;
import com.board.member.controller.auth.dto.request.SignUpRequest;
import com.board.member.controller.auth.dto.response.SignUpResponse;
import com.board.member.domain.auth.TokenProvider;
import com.board.member.domain.member.Member;
import com.board.member.repository.MemberRepository;
import com.board.member.service.auth.exception.NotExistLoginIdException;
import com.board.member.service.auth.exception.NotExistMemberException;
import com.board.member.service.auth.exception.NotExistNickNameException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
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

    @Transactional(readOnly = true)
    public String login(LoginRequest request) {
        Member member = findMemberByLoginId(request.loginId());
        member.checkPassword(request.password());

        return tokenProvider.create(member.getId());
    }

    private Member findMemberByLoginId(String loginId) {
        return memberRepository.findMemberByMemberLoginId(loginId)
                .orElseThrow(NotExistMemberException::new);
    }

    private void checkDuplicateLoginId(String loginId) {
        if(memberRepository.existsByMemberLoginId(loginId)) {
            throw new NotExistLoginIdException();
        }
    }

    private void checkDuplicateNickName(String memberNickName) {
        if(memberRepository.existsByMemberNickName(memberNickName)) {
            throw new NotExistNickNameException();
        }
    }
}
