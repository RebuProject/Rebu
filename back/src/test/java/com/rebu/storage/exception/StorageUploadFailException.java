package com.rebu.storage.exception;

import com.rebu.common.exception.CustomException;

public class StorageUploadFailException extends CustomException {
    public StorageUploadFailException() {
        super(StorageExceptionConstants.UPLOAD_FAIL);
    }
}
