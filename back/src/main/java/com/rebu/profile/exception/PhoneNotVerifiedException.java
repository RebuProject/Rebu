package com.rebu.profile.exception;

import com.rebu.common.exception.CustomException;

public class PhoneNotVerifiedException extends CustomException {
    public PhoneNotVerifiedException() {
        super(ProfileExceptionConstants.PHONE_NOT_VERIFIED);
    }
}
