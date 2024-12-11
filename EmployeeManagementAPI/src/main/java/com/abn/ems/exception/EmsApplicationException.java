package com.abn.ems.exception;

import java.io.Serial;

/**
 * Custom exception class for handling application-specific errors in the Employee Management System (EMS).
 *
 * <p>The {@code EmsApplicationException} is used to signal business logic or system-related
 * errors that occur within the EMS application. It provides detailed error messages and an
 * optional cause to help identify and debug issues effectively.</p>
 */

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
