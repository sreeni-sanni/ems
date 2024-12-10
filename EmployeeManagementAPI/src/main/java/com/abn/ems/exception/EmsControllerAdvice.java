package com.abn.ems.exception;

import com.abn.ems.model.ErrorResponse;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.abn.ems.constant.Constant.*;

@RestControllerAdvice
public class EmsControllerAdvice {

    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleRoleNotFoundException(RoleNotFoundException exception, WebRequest webRequest) {
        return ResponseEntity.badRequest().body(getErrorDetails(exception.getMessage(), HttpStatus.BAD_REQUEST, webRequest));
    }
    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAuthorizationDeniedException(AuthorizationDeniedException exception, WebRequest webRequest) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(getErrorDetails(exception.getMessage(), HttpStatus.UNAUTHORIZED, webRequest));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(Exception exception, WebRequest webRequest) {
        return ResponseEntity.internalServerError().body(getErrorDetails(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, webRequest));
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(MethodArgumentNotValidException exception, WebRequest webRequest) {
        return ResponseEntity.badRequest().body(getErrorDetails(exception.getMessage(), HttpStatus.BAD_REQUEST, webRequest));
    }

    @ExceptionHandler(ResourceAccessException.class)
    public ResponseEntity<ErrorResponse> handleResourceAccessException(ResourceAccessException exception, WebRequest webRequest) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(getErrorDetails(RESOURCE_UNAVAILABLE, HttpStatus.SERVICE_UNAVAILABLE, webRequest));
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(HandlerMethodValidationException exception, WebRequest webRequest) {
        Optional<String> errorMessage = exception.getAllErrors().stream().map(MessageSourceResolvable::getDefaultMessage).findAny();
        return errorMessage.map(s -> ResponseEntity.badRequest().body(getErrorDetails(s, HttpStatus.BAD_REQUEST, webRequest))).orElseGet(() -> ResponseEntity.badRequest().body(getErrorDetails(exception.getMessage(), HttpStatus.BAD_REQUEST, webRequest)));
    }

    @ExceptionHandler({SignatureException.class, ExpiredJwtException.class})
    public ResponseEntity<ErrorResponse> handleEmployeeNotFoundException(RuntimeException exception, WebRequest webRequest) {
        if(exception instanceof SignatureException) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(getErrorDetails(INVALID_JWT_TOKEN, HttpStatus.UNAUTHORIZED, webRequest));
        }else if(exception instanceof ExpiredJwtException) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(getErrorDetails(TOKEN_EXPIRED, HttpStatus.UNAUTHORIZED, webRequest));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(getErrorDetails(exception.getMessage(), HttpStatus.UNAUTHORIZED, webRequest));
    }

    private ErrorResponse getErrorDetails(String message, HttpStatus status, WebRequest webRequest) {
        return new ErrorResponse(message, status.value(), webRequest.getDescription(false), LocalDateTime.now());
    }


}
