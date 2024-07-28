package com.rebu.common.security.exception;

import com.rebu.common.exception.CustomException;

public class RefreshInvalidException extends CustomException {
    public RefreshInvalidException() {
        super(SecurityExceptionConstants.REFRESH_INVALID);
    }
}
