package com.abn.ems.exception;


import com.abn.ems.model.ErrorResponse;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.context.request.WebRequest;

import static com.abn.ems.constants.Constant.INVALID_JWT_TOKEN;
import static com.abn.ems.constants.Constant.TOKEN_EXPIRED;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * <p> Test class for {@link EmsControllerAdvice }</p>
 */
@ExtendWith(MockitoExtension.class)
public class EmsControllerAdviceTest {

    public static final int UNAUTHORIZED_STATUS_CODE = 401;
    public static final int BAD_REQUEST = 400;
    public static final int INTERNAL_SERVER_ERROR = 500;
    public static final int SERVICE_UNAVAILABLE = 503;
    public static final String ROLE_NOT_FOUND_EXCEPTION = "roleNotFoundException";
    public static final String EXCEPTION = "exception";


    @InjectMocks
    EmsControllerAdvice emsControllerAdvice;

    @Mock
    WebRequest webRequest;

    @Mock
    RuntimeException runtimeException;

    @Mock
    JwtException jwtException;

    @Mock
    AuthorizationDeniedException authorizationDeniedException;

    @Mock
    MethodArgumentNotValidException methodArgumentNotValidException;

    @Mock
    ResourceAccessException resourceAccessException;

    @Test
    void testRoleNotFoundException(){
        ResponseEntity<ErrorResponse> response = emsControllerAdvice.handleRoleNotFoundException(new RoleNotFoundException(ROLE_NOT_FOUND_EXCEPTION),webRequest);
        assertNotNull(response.getBody());
        assertEquals(response.getBody().statusCode(), BAD_REQUEST);
        assertEquals(response.getBody().message(), ROLE_NOT_FOUND_EXCEPTION);
    }

    @Test
    void testSignatureException(){
        ResponseEntity<ErrorResponse> response = emsControllerAdvice.handleJwtException(new JwtException(INVALID_JWT_TOKEN),webRequest);
        assertNotNull(response.getBody());
        assertEquals(response.getBody().statusCode(), UNAUTHORIZED_STATUS_CODE);
        assertEquals(response.getBody().message(), INVALID_JWT_TOKEN);
    }

    @Test
    void testExpiredJwtException(){
        ResponseEntity<ErrorResponse> response = emsControllerAdvice.handleJwtException(new JwtException(TOKEN_EXPIRED),webRequest);
        assertNotNull(response.getBody());
        assertEquals(response.getBody().statusCode(), UNAUTHORIZED_STATUS_CODE);
        assertEquals(response.getBody().message(), TOKEN_EXPIRED);
    }

    @Test
    void testAuthorizationDeniedException(){
        ResponseEntity<ErrorResponse> response = emsControllerAdvice.handleAuthorizationDeniedException(authorizationDeniedException,webRequest);
        assertNotNull(response.getBody());
        assertEquals(response.getBody().statusCode(), UNAUTHORIZED_STATUS_CODE);
    }

    @Test
    void testGlobalException(){
        ResponseEntity<ErrorResponse> response = emsControllerAdvice.handleGlobalException(new Exception(EXCEPTION),webRequest);
        assertNotNull(response.getBody());
        assertEquals(response.getBody().message(), EXCEPTION);
        assertEquals(response.getBody().statusCode(), INTERNAL_SERVER_ERROR);
    }

    @Test
    void testMethodArgumentNotValidException(){
        ResponseEntity<ErrorResponse> response = emsControllerAdvice.handleGlobalException(methodArgumentNotValidException,webRequest);
        assertNotNull(response.getBody());
        assertEquals(response.getBody().statusCode(), BAD_REQUEST);
    }

    @Test
    void testResourceAccessException(){
        ResponseEntity<ErrorResponse> response = emsControllerAdvice.handleResourceAccessException(resourceAccessException,webRequest);
        assertNotNull(response.getBody());
        assertEquals(response.getBody().statusCode(), SERVICE_UNAVAILABLE);
    }
}
