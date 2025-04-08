package com.board.global.exception;

import com.board.common.exception.exceptions.BaseErrorCode;
import org.springframework.http.HttpStatus;

public enum GlobalErrorCode implements BaseErrorCode {

    NOT_FOUND_TOKEN(HttpStatus.NOT_FOUND, "T001", "토큰을 찾을 수 없습니다."),
    INVALID_TOKEN(HttpStatus.BAD_REQUEST, "T002", "토큰이 유효하지 않습니다."),
    CAN_NOT_VERIFY_TOKEN(HttpStatus.BAD_REQUEST, "T003", "토큰을 검증할 수 없습니다."),
    EXPIRED_TOKEN(HttpStatus.CONFLICT, "T004", "토큰 유효기간이 만료되었습니다.");

    private final HttpStatus httpStatus;
    private final String customCode;
    private final String message;

    GlobalErrorCode(HttpStatus httpStatus, String customCode, String message) {
        this.httpStatus = httpStatus;
        this.customCode = customCode;
        this.message = message;
    }

    @Override
    public HttpStatus httpStatus() {
        return httpStatus;
    }

    @Override
    public String customCode() {
        return customCode;
    }

    @Override
    public String message() {
        return message;
    }
}
