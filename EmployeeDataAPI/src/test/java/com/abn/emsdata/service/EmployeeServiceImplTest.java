package com.abn.emsdata.service;

import com.abn.emsdata.entity.Employee;
import com.abn.emsdata.entity.Role;
import com.abn.emsdata.exception.EmployeeNotFoundException;
import com.abn.emsdata.mapper.EmployeeMapper;
import com.abn.emsdata.model.EmployeeDataRequest;
import com.abn.emsdata.model.EmployeeDataResponse;
import com.abn.emsdata.model.ResponseMessage;
import com.abn.emsdata.repository.EmployeeRepository;
import com.abn.emsdata.repository.RoleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


/** <p> Test class for {@link EmployeeService }</p>
 *
 */
@ExtendWith(MockitoExtension.class)
public class EmployeeServiceImplTest {

    public static final String ADMIN = "ADMIN";
    public static final String USER = "USER";
    public static final Long EMPLOYEE_ID = 1L;
    public static final Long ROLE_ID = 1L;
    @InjectMocks
    EmployeeServiceImpl employeeServiceImpl;

    @Mock
    EmployeeRepository employeeRepository;

    @Mock
    EmployeeMapper employeeMapper;

    @Mock
    RoleRepository roleRepository;

    @Mock
    Employee employee;

    @Mock
    Role role;

    @Test
    void testCreateEmployee(){
        when(employeeMapper.toEmployee(any())).thenReturn(employee);
        when(roleRepository.findById(any())).thenReturn(Optional.of(role));
        when(employeeRepository.save(employee)).thenReturn(employee);
        when(employeeMapper.toEmployeeDataResponse(employee)).thenReturn(new EmployeeDataResponse(EMPLOYEE_ID,ADMIN,ROLE_ID));
        EmployeeDataResponse response = employeeServiceImpl.create(new EmployeeDataRequest(ADMIN,1L));
        assertNotNull(response);
        assertEquals(response.name(),ADMIN);
    }

    @Test
    void testGetEmployeeById(){
        when(employeeRepository.findById(any())).thenReturn(Optional.ofNullable(employee));
        when(employeeMapper.toEmployeeDataResponse(employee)).thenReturn(new EmployeeDataResponse(EMPLOYEE_ID,ADMIN,ROLE_ID));
        EmployeeDataResponse response = employeeServiceImpl.getEmployeeById(1L);
        assertNotNull(response);
        assertEquals(response.roleId(),1L);
    }

    @Test
    void testUpdateEmployee(){
        when(employeeRepository.findById(any())).thenReturn(Optional.ofNullable(getEmployee()));
        when(employeeMapper.toEmployeeDataResponse(any())).thenReturn(new EmployeeDataResponse(EMPLOYEE_ID, USER,ROLE_ID));
        when(employeeRepository.save(any())).thenReturn(getEmployee());
        EmployeeDataResponse response = employeeServiceImpl.update(1L,new EmployeeDataRequest(USER,ROLE_ID));
        assertNotNull(response);
        assertEquals(response.name(), USER);
    }

    @Test
    void testUpdateEmployeeWithDifferentRoleId(){
        when(employeeRepository.findById(any())).thenReturn(Optional.ofNullable(getEmployee()));
        when(employeeMapper.toEmployeeDataResponse(any())).thenReturn(new EmployeeDataResponse(EMPLOYEE_ID, USER,ROLE_ID));
        when(employeeRepository.save(any())).thenReturn(getEmployee());
        when(roleRepository.findById(any())).thenReturn(Optional.of(role));
        EmployeeDataResponse response = employeeServiceImpl.update(1L,new EmployeeDataRequest(USER,2L));
        assertNotNull(response);
        assertEquals(response.roleId(), 1L);
    }


    @Test
    void testDeleteEmployee(){
        when(employeeRepository.findById(any())).thenReturn(Optional.ofNullable(employee));
        assertEquals(employeeServiceImpl.delete(EMPLOYEE_ID).message(),"Employee deleted successfully");
    }

    @Test
    void testDeleteEmployeeForNotFoundScenario(){
        assertThrows(EmployeeNotFoundException.class,()-> employeeServiceImpl.delete(2L));
    }

    private Employee getEmployee(){
        Employee employee = new Employee();
        employee.setId(1L);
        employee.setFirstName("firstName");
        employee.setSurName("surName");
        employee.setRole(getRoleDetails());
        return employee;
    }

    private Role getRoleDetails() {
        Role role = new Role();
        Employee employee = new Employee();
        employee.setId(EMPLOYEE_ID);
        role.setId(ROLE_ID);
        role.setEmployee(List.of(employee));
        return role;
    }
}
