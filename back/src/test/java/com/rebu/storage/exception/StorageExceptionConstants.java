package com.rebu.storage.exception;

import com.rebu.common.exception.ExceptionConstants;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StorageExceptionConstants implements ExceptionConstants {
    CONNECTION_ERR("0E00"), UPLOAD_FAIL("0E01");
    private final String code;
}
