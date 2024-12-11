package com.abn.ems.controller;

import com.abn.ems.auth.AuthController;
import com.abn.ems.auth.AuthRequest;
import com.abn.ems.auth.JwtUtilService;
import com.abn.ems.model.EmployeeResponse;
import com.abn.ems.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/** <p> Test class for {@link AuthController }</p>
 *
 */
@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {

   private static final long EMPLOYEE_ID = 1L;
   private static final Long ROLE_ID = 1L;
   public static final String SUR_NAME = "surName";
   public static final String FIRST_NAME = "firstName";
   public static final String TOKEN = "token";
   public static final String USER = "user";

   @InjectMocks
   AuthController authController;

   @Mock
   JwtUtilService jwtUtilService;

   @Mock
   EmployeeService employeeService;

   @Test
   void testGetToken(){
     when(employeeService.getUser(any())).thenReturn(getEmployeeResponse(USER));
     when(jwtUtilService.generateToken(any(),any())).thenReturn(TOKEN);
     var response = authController.getToken(getAuthRequest());
     assertNotNull(response);
     assertEquals(response.getBody(), TOKEN);
   }

   @Test
   void testGetToken_ForException(){
    when(employeeService.getUser(any())).thenReturn(getEmployeeResponse(SUR_NAME));
    assertThrows(RuntimeException.class,()->authController.getToken(getAuthRequest()));
   }

   private AuthRequest getAuthRequest() {
     AuthRequest authRequest = new AuthRequest("USER");
     return authRequest;
   }

   private EmployeeResponse getEmployeeResponse(String surName) {
    return new EmployeeResponse(EMPLOYEE_ID, FIRST_NAME, surName, ROLE_ID);
   }
}
