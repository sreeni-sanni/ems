package com.abn.emsdata.exception;

import java.io.Serial;

public class EmployeeNotFoundException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public EmployeeNotFoundException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public EmployeeNotFoundException(String message) {
        this(message, null);
    }
}
