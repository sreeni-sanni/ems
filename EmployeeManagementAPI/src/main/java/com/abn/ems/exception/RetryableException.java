package com.abn.ems.exception;

import java.io.Serial;

public class RetryableException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public RetryableException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public RetryableException(String message) {
        this(message, null);
    }
}
