package com.rebu.member.exception;

import com.rebu.common.exception.CustomException;

public class EmailNotVerifiedException extends CustomException {
    public EmailNotVerifiedException() {
        super(MemberExceptionConstants.EMAIL_NOT_VERIFIED);
    }
}
