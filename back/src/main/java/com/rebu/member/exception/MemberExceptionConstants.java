package com.rebu.member.exception;

import com.rebu.common.exception.ExceptionConstants;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MemberExceptionConstants implements ExceptionConstants {
    EMAIL_MISMATCH("0000"),
    PASSWORD_MISMATCH("0001"),
    NAME_MISMATCH("0002"),
    BIRTH_MISMATCH("0003"),
    GENDER_MISMATCH("0004");

    final String code;
}
