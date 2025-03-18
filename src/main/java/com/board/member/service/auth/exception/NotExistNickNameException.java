package com.board.member.service.auth.exception;

import com.board.member.exception.exceptions.MemberErrorCode;
import com.board.member.exception.exceptions.MemberException;

public class NotExistNickNameException extends MemberException {

    public NotExistNickNameException() {
        super(MemberErrorCode.DUPLICATE_NICKNAME);
    }
}
