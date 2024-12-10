package com.abn.ems.exception;

import java.io.Serial;

public class EmsApplicationException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public EmsApplicationException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public EmsApplicationException(String message) {
        this(message, null);
    }
}
