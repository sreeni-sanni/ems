package com.abn.emsdata.exception;

import com.abn.emsdata.model.ErrorDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

import static com.abn.emsdata.constant.Constant.EXCEPTION_OCCURRED;

/**
 * Global exception handler for EMS (Employee data api).
 * This class handles exceptions thrown by controllers in the application
 * and returns appropriate HTTP responses with relevant error messages.
 *
 * <p>By using {@link EMSDataControllerAdvice}, we can catch and handle specific exceptions
 * thrown by any controller method, ensuring that all API responses are consistent and
 * meaningful for the client.
 */
@Slf4j
@ControllerAdvice
public class EMSDataControllerAdvice {

    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleRoleNotFoundException(RoleNotFoundException exception, WebRequest webRequest) {
        log.error(EXCEPTION_OCCURRED, exception.getMessage(), exception);
        return ResponseEntity.badRequest().body(getErrorDetails(exception.getMessage(),webRequest));
    }

    @ExceptionHandler(EmployeeNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleEmployeeNotFoundException(EmployeeNotFoundException exception,WebRequest webRequest) {
        log.error(EXCEPTION_OCCURRED, exception.getMessage(), exception);
        return ResponseEntity.badRequest().body(getErrorDetails(exception.getMessage(),webRequest));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleGlobalException(Exception exception,WebRequest webRequest) {
        log.error(EXCEPTION_OCCURRED, exception.getMessage(), exception);
        return ResponseEntity.internalServerError().body(getErrorDetails(exception.getMessage(),webRequest));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDetails> handleGlobalException(MethodArgumentNotValidException exception, WebRequest webRequest) {
        log.error(EXCEPTION_OCCURRED, exception.getMessage(), exception);
        return ResponseEntity.badRequest().body(getErrorDetails(exception.getMessage(), webRequest));
    }
    private ErrorDetails getErrorDetails(String message, WebRequest webRequest) {
        return new ErrorDetails(message,webRequest.getDescription(false),LocalDateTime.now());
    }


}
