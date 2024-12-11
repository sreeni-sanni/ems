package com.abn.ems.exception;

import java.io.Serial;

/**
 * Exception thrown when a specified role cannot be found in the Employee Management System (EMS).
 *
 * <p>The {@code RoleNotFoundException} is used to indicate that the requested role does not
 * exist in the system. This exception is typically thrown during role-based operations such as
 * validation or authorization checks.</p>
 */
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

