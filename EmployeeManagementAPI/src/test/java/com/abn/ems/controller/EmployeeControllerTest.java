package com.abn.ems.controller;

import com.abn.ems.model.EmployeeRequest;
import com.abn.ems.model.EmployeeResponse;
import com.abn.ems.model.ResponseMessage;
import com.abn.ems.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/** <p> Test class for {@link EmployeeController }</p>
 *
 */
@ExtendWith(MockitoExtension.class)
public class EmployeeControllerTest {

    private static final long EMPLOYEE_ID = 1L;
    private static final Long ROLE_ID = 1L;
    public static final String SUR_NAME = "surName";
    public static final String FIRST_NAME = "firstName";
    public static final String UPDATED_FIRST_NAME = "updatedFirstName";
    public static final String ROLE_ADMIN = "ROLE_ADMIN";

    @InjectMocks
    EmployeeController employeeController;

    @Mock
    EmployeeService employeeService;

    @Test
    void testCreateEmployee(){
        when(employeeService.create(any(),any())).thenReturn(getEmployeeResponse(FIRST_NAME));
        var response = employeeController.employee(getEmployeeRequest(FIRST_NAME), ROLE_ADMIN);
        assertNotNull(response.getBody());
        assertEquals(response.getBody().id(),EMPLOYEE_ID);
        assertEquals(response.getBody().firstName(), FIRST_NAME);
        assertEquals(response.getStatusCode(), HttpStatus.CREATED);
    }

    @Test
    void testUpdateEmployee(){
        when(employeeService.update(any(),any())).thenReturn(getEmployeeResponse(UPDATED_FIRST_NAME));
        var response = employeeController.updateEmployee(ROLE_ID, getEmployeeRequest(UPDATED_FIRST_NAME));
        assertNotNull(response.getBody());
        assertEquals(response.getBody().id(),EMPLOYEE_ID);
        assertEquals(response.getBody().firstName(), UPDATED_FIRST_NAME);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    void testGetEmployeeById(){
        when(employeeService.getEmployee(any())).thenReturn(getEmployeeResponse(FIRST_NAME));
        var response = employeeController.getEmployee(EMPLOYEE_ID);
        assertNotNull(response.getBody());
        assertEquals(response.getBody().id(),EMPLOYEE_ID);
        assertEquals(response.getBody().surName(), SUR_NAME);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    void testDeleteEmployee(){
        when(employeeService.delete(any())).thenReturn(getResponseMessage());
        var response = employeeController.deleteEmployee(ROLE_ID);
        assertNotNull(response.getBody());
        assertEquals(response.getBody().message(),"employee deleted");
        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    private EmployeeResponse getEmployeeResponse(String updatedFirstName) {
        return new EmployeeResponse(EMPLOYEE_ID, updatedFirstName, SUR_NAME, ROLE_ID);
    }
    private ResponseMessage getResponseMessage() {
        return new ResponseMessage("employee deleted");
    }

    private static EmployeeRequest getEmployeeRequest(String firstName) {
        return new EmployeeRequest(firstName, SUR_NAME, ROLE_ID);
    }
}
