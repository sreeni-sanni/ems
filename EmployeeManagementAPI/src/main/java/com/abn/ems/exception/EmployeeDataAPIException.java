package com.abn.ems.exception;

import java.io.Serial;

/**
 * Exception class for handling errors specific to the EmployeeDataAPI API calls.
 * <p>
 * This exception is used to capture and represent errors that occur during
 * interactions with the EmployeeDataAPI, such as issues with database access,
 * invalid data input, or communication problems with downstream services.
 * </p>
 */
 public class EmployeeDataAPIException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * @param message
     * @param throwable
     */
    public EmployeeDataAPIException(String message, Throwable throwable) {
        super(message, throwable);
    }

    /**
     * @param message
     */
    public EmployeeDataAPIException(String message) {
        this(message, null);
    }
}
