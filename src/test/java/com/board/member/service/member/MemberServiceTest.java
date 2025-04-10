package com.board.member.service.member;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

import com.board.member.controller.member.dto.reponse.MemberResponse;
import com.board.member.controller.member.dto.request.MemberRequest;
import com.board.member.domain.member.Member;
import com.board.member.exception.MemberErrorCode;
import com.board.member.exception.MemberException;
import com.board.member.repository.MemberRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@SuppressWarnings("NonAsciiCharacters")
class MemberServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private MemberService memberService;

    private Member member;

    @BeforeEach
    void set() {
        member = new Member("신짱구", "짱구", "aaa", "password123");
    }

    @Nested
    class 정상_동작_테스트를_진행한다 {

        @Test
        @DisplayName("멤버 아이디에 해당하는 유저를 보여준다.")
        void showMember() {
            // given
            Long memberId = 1L;
            given(memberRepository.findMemberById(memberId)).willReturn(Optional.of(member));

            // when
            MemberResponse response = memberService.showMember(memberId);

            // then
            assertThat(response.name()).isEqualTo("신짱구");
        }

        @Test
        void updateMember() {
            // given
            Long memberId = 1L;
            MemberRequest request = new MemberRequest(
                    "홍길동",
                    "길동",
                    "sss",
                    "123"
            );
            given(memberRepository.findMemberById(memberId)).willReturn(Optional.ofNullable(member));

            // when
            MemberResponse response = memberService.updateMember(memberId, request);

            // then
            assertThat(response.name()).isEqualTo("홍길동");
        }

        @Test
        void deleteMember() {
            // given
            Long memberId = 1L;
            given(memberRepository.findMemberById(memberId)).willReturn(Optional.of(member));

            // when
            MemberResponse response = memberService.deleteMember(memberId);

            // then
            assertThat(response.name()).isEqualTo("신짱구");
        }
    }

    @Nested
    class 예외_처리_테스트를_진행한다 {

        @Test
        @DisplayName("멤버 아이디에 해당하는 유저가 없을 경우 예외를 반환한다.")
        void showMemberException() {
            // given
            Long memberId = 2L;
            given(memberRepository.findMemberById(memberId)).willReturn(Optional.empty());

            // when & then
            assertThatThrownBy(() -> memberService.showMember(memberId))
                    .isInstanceOf(MemberException.class)
                    .hasMessageContaining(MemberErrorCode.NOT_FOUND_MEMBER.message());
        }
    }
}
