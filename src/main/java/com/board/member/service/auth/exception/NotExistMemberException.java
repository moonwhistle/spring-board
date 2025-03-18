package com.board.member.service.auth.exception;

import com.board.member.exception.exceptions.MemberErrorCode;
import com.board.member.exception.exceptions.MemberException;

public class NotExistMemberException extends MemberException {

    public NotExistMemberException() {
        super(MemberErrorCode.NOT_EXIST_MEMBER);
    }
}
