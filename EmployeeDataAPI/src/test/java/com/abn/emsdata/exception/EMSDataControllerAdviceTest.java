package com.abn.emsdata.exception;

import com.abn.emsdata.model.ErrorDetails;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class EMSDataControllerAdviceTest {

    @InjectMocks
    EMSDataControllerAdvice emsDataControllerAdvice;

    @Mock
    WebRequest webRequest;

    @Test
    public void testEmployeeNotFoundException() {
        EmployeeNotFoundException employeeNotFoundException = new EmployeeNotFoundException("employeeException");
        ResponseEntity<ErrorDetails> response = emsDataControllerAdvice.handleEmployeeNotFoundException(employeeNotFoundException,webRequest);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(response.getBody().message(),"employeeException");
    }

    @Test
    public void testRoleNotFoundException() {
        RoleNotFoundException roleNotFoundException = new RoleNotFoundException("roleNotFoundException");
        ResponseEntity<ErrorDetails> response = emsDataControllerAdvice.handleRoleNotFoundException(roleNotFoundException,webRequest);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(response.getBody().message(),"roleNotFoundException");
    }

    @Test
    public void handleGlobalException() {
        Exception globalException = new Exception("globalException");
        ResponseEntity<ErrorDetails> response = emsDataControllerAdvice.handleGlobalException(globalException,webRequest);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(response.getBody().message(),"globalException");
    }
}