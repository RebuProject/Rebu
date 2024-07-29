package com.rebu.auth.exception;

import com.rebu.common.exception.ExceptionConstants;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AuthExceptionConstants implements ExceptionConstants {

    AUTH_PURPOSE_INVALID("유효하지 않은 인증 목적"),
    PASSWORD_AUTH_FAIL("비밀번호 인증 실패");

    final String code;
}
