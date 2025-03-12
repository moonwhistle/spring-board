package com.board.member.exception.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum MemberErrorCode {
    ;

    private final HttpStatus httpStatus;
    private final String customCode;
    private final String message;

    MemberErrorCode(HttpStatus httpStatus, String customCode, String message) {
        this.httpStatus = httpStatus;
        this.customCode = customCode;
        this.message = message;
    }
}
