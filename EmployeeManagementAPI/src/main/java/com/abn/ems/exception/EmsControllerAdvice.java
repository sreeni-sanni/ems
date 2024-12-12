package com.abn.ems.exception;

import com.abn.ems.model.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

import static com.abn.ems.constants.Constant.*;
/**
 * Global exception handler for the Employee Management System (EMS) application.
 *
 * <p>The {@code EmsControllerAdvice} class is annotated with {@link org.springframework.web.bind.annotation.ControllerAdvice}
 * and provides centralized exception handling across all controllers in the EMS application.
 * It ensures that application-specific and system exceptions are properly translated into meaningful
 * HTTP responses for clients.</p>
 *
 * <h3>Key Features:</h3>
 * <ul>
 *     <li>Handles custom application exceptions (e.g., {@link com.abn.ems.exception.EmsApplicationException}).</li>
 *     <li>Translates general exceptions into appropriate HTTP status codes.</li>
 *     <li>Provides detailed error messages and debugging information in the response.</li>
 * </ul>
 */
@Slf4j
@RestControllerAdvice
public class EmsControllerAdvice {

    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleRoleNotFoundException(RoleNotFoundException exception, WebRequest webRequest) {
        log.error(EXCEPTION_OCCURRED, exception.getMessage(), exception);
        return ResponseEntity.badRequest().body(getErrorDetails(exception.getMessage(), HttpStatus.BAD_REQUEST, webRequest));
    }
    @ExceptionHandler(EmsApplicationException.class)
    public ResponseEntity<ErrorResponse> handleEmsApplicationException(EmsApplicationException exception, WebRequest webRequest) {
        log.error(EXCEPTION_OCCURRED, exception.getMessage(), exception);
        return ResponseEntity.badRequest().body(getErrorDetails(exception.getMessage(), HttpStatus.BAD_REQUEST, webRequest));
    }
    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAuthorizationDeniedException(AuthorizationDeniedException exception, WebRequest webRequest) {
        log.error(EXCEPTION_OCCURRED, exception.getMessage(), exception);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(getErrorDetails(ACCESS_DENIED, HttpStatus.UNAUTHORIZED, webRequest));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(Exception exception, WebRequest webRequest) {
        log.error(EXCEPTION_OCCURRED, exception.getMessage(), exception);
        return ResponseEntity.internalServerError().body(getErrorDetails(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, webRequest));
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(MethodArgumentNotValidException exception, WebRequest webRequest) {
        log.error(EXCEPTION_OCCURRED, exception.getMessage(), exception);
        return ResponseEntity.badRequest().body(getErrorDetails(exception.getMessage(), HttpStatus.BAD_REQUEST, webRequest));
    }

    @ExceptionHandler(ResourceAccessException.class)
    public ResponseEntity<ErrorResponse> handleResourceAccessException(ResourceAccessException exception, WebRequest webRequest) {
        log.error(EXCEPTION_OCCURRED, exception.getMessage(), exception);
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(getErrorDetails(RESOURCE_UNAVAILABLE, HttpStatus.SERVICE_UNAVAILABLE, webRequest));
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ErrorResponse> handleJwtException(JwtException exception, WebRequest webRequest) {
        log.error(EXCEPTION_OCCURRED, exception.getMessage(), exception);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(getErrorDetails(exception.getMessage(), HttpStatus.UNAUTHORIZED, webRequest));
    }

    private ErrorResponse getErrorDetails(String message, HttpStatus status, WebRequest webRequest) {
        return new ErrorResponse(message, status.value(), webRequest.getDescription(false), LocalDateTime.now());
    }


}
