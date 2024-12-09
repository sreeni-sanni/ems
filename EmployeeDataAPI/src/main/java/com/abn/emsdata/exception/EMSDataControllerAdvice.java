package com.abn.emsdata.exception;

import com.abn.emsdata.model.ErrorDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class EMSDataControllerAdvice {

    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleRoleNotFoundException(RoleNotFoundException exception, WebRequest webRequest) {
        return ResponseEntity.badRequest().body(getErrorDetails(exception.getMessage(),webRequest));
    }

    @ExceptionHandler(EmployeeNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleEmployeeNotFoundException(EmployeeNotFoundException exception,WebRequest webRequest) {
        return ResponseEntity.badRequest().body(getErrorDetails(exception.getMessage(),webRequest));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleGlobalException(Exception exception,WebRequest webRequest) {
        return ResponseEntity.internalServerError().body(getErrorDetails(exception.getMessage(),webRequest));
    }
    private ErrorDetails getErrorDetails(String message, WebRequest webRequest) {
        return new ErrorDetails(message,webRequest.getDescription(false),LocalDateTime.now());
    }


}
