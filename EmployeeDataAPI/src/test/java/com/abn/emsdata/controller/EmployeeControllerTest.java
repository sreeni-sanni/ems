package com.abn.emsdata.controller;

import com.abn.emsdata.model.EmployeeDataRequest;
import com.abn.emsdata.model.EmployeeDataResponse;
import com.abn.emsdata.model.ResponseMessage;
import com.abn.emsdata.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import static com.abn.emsdata.service.EmployeeServiceImplTest.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/** <p> Test class for {@link EmployeeController }</p>
 *
 */
@ExtendWith(MockitoExtension.class)
public class EmployeeControllerTest {

    @InjectMocks
    EmployeeController employeeController;

    @Mock
    EmployeeService employeeService;

    @Test
    void testCreateEmployee(){
        when(employeeService.create(any())).thenReturn(new EmployeeDataResponse(EMPLOYEE_ID, USER,ROLE_ID));
        var response = employeeController.employee(new EmployeeDataRequest(USER,ROLE_ID));
        assertNotNull(response.getBody());
        assertEquals(response.getBody().id(),EMPLOYEE_ID);
        assertEquals(response.getBody().name(),USER);
        assertEquals(response.getStatusCode(), HttpStatus.CREATED);
    }

    @Test
    void testUpdateEmployee(){
        when(employeeService.update(any(),any())).thenReturn(new EmployeeDataResponse(EMPLOYEE_ID, USER,2L));
        var response = employeeController.updateEmployee(EMPLOYEE_ID,new EmployeeDataRequest(USER,2L));
        assertNotNull(response.getBody());
        assertEquals(response.getBody().id(),EMPLOYEE_ID);
        assertEquals(response.getBody().name(),USER);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    void testGetEmployeeById(){
        when(employeeService.getEmployeeById(any())).thenReturn(new EmployeeDataResponse(EMPLOYEE_ID, USER,ROLE_ID));
        var response = employeeController.getEmployee(EMPLOYEE_ID);
        assertNotNull(response.getBody());
        assertEquals(response.getBody().id(),EMPLOYEE_ID);
        assertEquals(response.getBody().name(),USER);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    void testDeleteEmployee(){
        when(employeeService.delete(any())).thenReturn(new ResponseMessage("Employee deleted successfully"));
        var response = employeeController.deleteEmployee(EMPLOYEE_ID);
        assertNotNull(response.getBody());
        assertEquals(response.getBody().message(),"Employee deleted successfully");
        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }
}
