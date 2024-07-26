package com.rebu.member.exception;

import com.rebu.common.exception.ExceptionConstants;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MemberExceptionConstants implements ExceptionConstants {
    EMAIL_MISMATCH("000"),
    PASSWORD_MISMATCH("0001");

    final String code;
}
