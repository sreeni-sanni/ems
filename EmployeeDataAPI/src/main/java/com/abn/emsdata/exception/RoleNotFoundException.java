package com.abn.emsdata.exception;

import java.io.Serial;

/**
 * Exception thrown when a role with the specified name or ID cannot be found in the system.
 * This exception is typically thrown when attempting to access, update, or delete a role that
 * does not exist in the database or system.
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
