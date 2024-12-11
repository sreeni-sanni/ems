package com.abn.emsdata.exception;

import java.io.Serial;

/**
 * Exception thrown when an employee with the specified ID cannot be found.
 * This is typically used in scenarios where an employee is requested but doesn't
 * exist in the database, or when an operation (such as update or delete) is
 * attempted on a non-existent employee.
 */
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
