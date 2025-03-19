package com.board.member.service.member;

import com.board.member.controller.member.dto.reponse.MemberResponse;
import com.board.member.domain.member.Member;
import com.board.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public MemberResponse getMember(Long memberId) {
        Member member = memberRepository.findMemberById(memberId);
        return new MemberResponse(
                member.getMemberName(),
                member.getMemberNickName(),
                member.getMemberLoginId(),
                member.getMemberPassword()
        );
    }
}
