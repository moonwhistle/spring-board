package com.board.article.exception.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ArticleErrorCode {
    ;

    private final HttpStatus httpStatus;
    private final String customCode;
    private final String message;

    ArticleErrorCode(HttpStatus httpStatus, String customCode, String message) {
        this.httpStatus = httpStatus;
        this.customCode = customCode;
        this.message = message;
    }
}
