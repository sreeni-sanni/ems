package com.abn.emsdata.exception;

import java.io.Serial;

public class RoleNotFoundException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = 1L;

    public RoleNotFoundException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public RoleNotFoundException(String message) {
        this(message, null);
    }
}
