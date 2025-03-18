package com.board.member.exception.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum MemberErrorCode {

    DUPLICATE_LOGIN_ID(HttpStatus.CONFLICT, "M001", "이미 존재하는 아이디 입니다."),
    DUPLICATE_NICKNAME(HttpStatus.CONFLICT, "M002", "이미 존재하는 닉네임 입니다.");


    private final HttpStatus httpStatus;
    private final String customCode;
    private final String message;

    MemberErrorCode(HttpStatus httpStatus, String customCode, String message) {
        this.httpStatus = httpStatus;
        this.customCode = customCode;
        this.message = message;
    }
}
