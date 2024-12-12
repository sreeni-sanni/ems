package com.abn.ems.exception;

import java.io.Serial;

/**
 * Exception thrown when a specified if jwt token expired or invalid.
 */
public class JwtException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * @param message
     * @param throwable
     */
    public JwtException(String message, Throwable throwable) {
        super(message, throwable);
    }

    /**
     * @param message
     */
    public JwtException(String message) {
        this(message, null);
    }
}
