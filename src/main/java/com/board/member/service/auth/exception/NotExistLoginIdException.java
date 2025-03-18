package com.board.member.service.auth.exception;

import com.board.member.exception.exceptions.MemberErrorCode;
import com.board.member.exception.exceptions.MemberException;

public class NotExistLoginIdException extends MemberException {

    public NotExistLoginIdException() {
        super(MemberErrorCode.DUPLICATE_LOGIN_ID);
    }
}
