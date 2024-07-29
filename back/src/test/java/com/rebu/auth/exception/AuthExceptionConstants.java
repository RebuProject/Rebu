package com.rebu.auth.exception;

import com.rebu.common.exception.ExceptionConstants;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AuthExceptionConstants implements ExceptionConstants {

    AUTH_PURPOSE_INVALID("유효하지 않은 인증 목적");

    final String code;
}
