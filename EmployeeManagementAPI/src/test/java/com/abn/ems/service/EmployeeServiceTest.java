package com.abn.ems.service;

import com.abn.ems.exception.RoleNotFoundException;
import com.abn.ems.mapper.EmployeeMapper;
import com.abn.ems.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.*;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import static com.abn.ems.constants.Constant.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/** <p> Test class for {@link EmployeeService }</p>
 *
 */
@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

    public static final String SUR_NAME = "surName";
    public static final String FIRST_NAME = "firstName";
    public static final long ROLE_ID = 1L;
    public static final long EMPLOYEE_ID = 1L;
    public static final String ADMIN = "ADMIN";
    public static final String USER = "USER";
    public static final String UPDATED_SUR_NAME = "updatedSurName";

    @InjectMocks
    EmployeeServiceImpl employeeService;

    @Mock
    RestTemplate restTemplate;

    @Mock
    EmployeeMapper employeeMapper;

    public static final String BASE_PATH = "basePath";

    @BeforeEach
    public void setUp(){
        ReflectionTestUtils.setField(employeeService,BASE_PATH,BASE_PATH);
    }


    @Test
    void testCreateEmployee(){
        when(employeeMapper.toEmployeeDataRequest(any())).thenReturn(getEmployeeDataRequest());
        when(restTemplate.postForObject(BASE_PATH + API_EMPLOYEE,getEmployeeDataRequest(),EmployeeDataResponse.class)).thenReturn(new EmployeeDataResponse(EMPLOYEE_ID,USER, ROLE_ID));
        when(employeeMapper.toEmployeeResponse(any())).thenReturn(new EmployeeResponse(EMPLOYEE_ID,FIRST_NAME, SUR_NAME, ROLE_ID));
        var response = employeeService.create(new EmployeeRequest(FIRST_NAME, SUR_NAME, ROLE_ID), ADMIN);
        assertNotNull(response);
        assertEquals(response.surName(), SUR_NAME);
    }

    @Test
    void testGetEmployee(){
        when(restTemplate.getForObject(BASE_PATH + API_EMPLOYEE_BY_ID, EmployeeDataResponse.class, EMPLOYEE_ID)).thenReturn(new EmployeeDataResponse(EMPLOYEE_ID,USER, ROLE_ID));
        when(employeeMapper.toEmployeeResponse(any())).thenReturn(new EmployeeResponse(EMPLOYEE_ID,FIRST_NAME, SUR_NAME, ROLE_ID));
        var response = employeeService.getEmployee(EMPLOYEE_ID);
        assertNotNull(response);
        assertEquals(response.firstName(),FIRST_NAME);
    }

    @Test
    void testGetUser(){
        when(restTemplate.getForObject(BASE_PATH + API_EMPLOYEE_BY_NAME, EmployeeDataResponse.class, USER)).thenReturn(new EmployeeDataResponse(EMPLOYEE_ID,USER, ROLE_ID));
        when(employeeMapper.toEmployeeResponse(any())).thenReturn(new EmployeeResponse(EMPLOYEE_ID,FIRST_NAME, SUR_NAME, ROLE_ID));
        var response = employeeService.getUser(USER);
        assertNotNull(response);
        assertEquals(response.roleId(),ROLE_ID);
    }

    @Test
    void testUpdateUser(){
        when(employeeMapper.toEmployeeDataRequest(any())).thenReturn(getEmployeeDataRequest());
        HttpEntity<EmployeeDataRequest> requestEntity = new HttpEntity<>(getEmployeeDataRequest());
        when(restTemplate.exchange(BASE_PATH + API_EMPLOYEE_BY_ID, HttpMethod.PUT,requestEntity,EmployeeDataResponse.class,EMPLOYEE_ID)).thenReturn(getResponseEntity());
        when(employeeMapper.toEmployeeResponse(any())).thenReturn(new EmployeeResponse(EMPLOYEE_ID,FIRST_NAME, UPDATED_SUR_NAME, ROLE_ID));
        var response = employeeService.update(EMPLOYEE_ID,new EmployeeRequest(FIRST_NAME, UPDATED_SUR_NAME, ROLE_ID));
        assertNotNull(response);
        assertEquals(response.roleId(),ROLE_ID);
    }

    @Test
    void testUpdateUserForException(){
        assertThrows(RoleNotFoundException.class,()-> employeeService.update(EMPLOYEE_ID,new EmployeeRequest(FIRST_NAME, UPDATED_SUR_NAME, 5L)));
    }

    @Test
    void testDeleteUser(){
        when(restTemplate.exchange(BASE_PATH + API_EMPLOYEE_BY_ID, HttpMethod.DELETE, getHeaders() , ResponseMessage.class, EMPLOYEE_ID)).thenReturn(new ResponseEntity<>(new ResponseMessage("deleted"),HttpStatus.OK));
        var response = employeeService.delete(1L);
        assertNotNull(response);
        assertEquals(response.message(),"deleted");
    }

    EmployeeDataRequest getEmployeeDataRequest(){
        EmployeeDataRequest employeeDataRequest = new EmployeeDataRequest();
        employeeDataRequest.setName("name");
        employeeDataRequest.setRoleId(ROLE_ID);
        return employeeDataRequest;
    }

    ResponseEntity<EmployeeDataResponse> getResponseEntity(){
        return new ResponseEntity<>(new EmployeeDataResponse(EMPLOYEE_ID,USER, ROLE_ID),HttpStatus.OK);
    }

    private HttpEntity<?> getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        return new HttpEntity<>(headers);
    }
}
