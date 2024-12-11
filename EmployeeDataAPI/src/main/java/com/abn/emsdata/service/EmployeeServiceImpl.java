package com.abn.emsdata.service;

import com.abn.emsdata.entity.Employee;
import com.abn.emsdata.entity.Role;
import com.abn.emsdata.exception.EmployeeNotFoundException;
import com.abn.emsdata.exception.RoleNotFoundException;
import com.abn.emsdata.mapper.EmployeeMapper;
import com.abn.emsdata.model.EmployeeDataRequest;
import com.abn.emsdata.model.EmployeeDataResponse;
import com.abn.emsdata.model.ResponseMessage;
import com.abn.emsdata.repository.EmployeeRepository;
import com.abn.emsdata.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.abn.emsdata.constant.Constant.*;

/**
 * Implementation of the {@link EmployeeService} interface, providing the business logic for managing employee records.
 * This service handles operations like creating, updating, retrieving, and deleting employees.
 *
 * <p>The implementation interacts with the {@link EmployeeRepository} to perform CRUD operations on Employee entities.
 * It handles validation and transformation of employee data before persistence and includes error handling for various operations.
 *
 * <p>All public methods are expected to be transactional and may throw custom exceptions, such as {@link EmployeeNotFoundException},
 * to indicate specific errors during the employee management process.
 *
 * @author [Your Name]
 * @version 1.0
 * @since 2024-12-11
 */

@Service
@Transactional
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeRepository employeeRepository;
    private RoleRepository roleRepository;
    private EmployeeMapper employeeMapper;

    /**
     * Creates a new employee.
     *
     * @param employeeDataRequest the employee object to be created
     * @return the created employee object
     * @throws RoleNotFoundException if the given role is invalid
     */
    @Override
    public EmployeeDataResponse create(EmployeeDataRequest employeeDataRequest) {
        Role role = getRole(employeeDataRequest.roleId());
        Employee employee = employeeMapper.toEmployee(employeeDataRequest);
        employee.setRole(role);
        Employee entity = employeeRepository.save(employee);
        return employeeMapper.toEmployeeDataResponse(entity);
    }

    /**
     * Retrieves an employee by their ID.
     *
     * @param employeeId the ID of the employee to retrieve
     * @return the employee with the given ID
     * @throws EmployeeNotFoundException if no employee with the given ID is found
     */
    @Override
    public EmployeeDataResponse getEmployeeById(Long employeeId) {
        return employeeMapper.toEmployeeDataResponse(getEmployee(employeeId));
    }

    /**
     * Retrieves an employee by their surName.
     *
     * @param surName the surName of the employee to retrieve
     * @return the employee with the given surName
     * @throws EmployeeNotFoundException if no employee with the given surName is found
     */
    @Override
    public EmployeeDataResponse getEmployeeBySurName(String surName) {
        Employee employee = employeeRepository.getEmployeeBySurName(surName).orElseThrow(() -> new EmployeeNotFoundException(String.format(EMPLOYEE_NOT_FOUND_BY_NAME, surName)));
        return employeeMapper.toEmployeeDataResponse(employee);
    }

    /**
     * Updates an existing employee's details.
     *
     * @param employeeId          the ID of the employee to update
     * @param employeeDataRequest the updated employee details
     * @return the updated employee object
     * @throws EmployeeNotFoundException if no employee with the given ID is found
     * @throws RoleNotFoundException     if the given role is invalid
     */
    @Override
    public EmployeeDataResponse update(Long employeeId, EmployeeDataRequest employeeDataRequest) {
        Employee employee = getEmployee(employeeId);

        if (!employee.getRole().getId().equals(employeeDataRequest.roleId())) {
            employee.setRole(getRole(employeeDataRequest.roleId()));
        }
        employeeMapper.updateEmployeeEntity(employeeDataRequest, employee);
        Employee updatedEmp = employeeRepository.save(employee);
        return employeeMapper.toEmployeeDataResponse(updatedEmp);
    }

    /**
     * Deletes an employee by their ID.
     *
     * @param employeeId the ID of the employee to delete
     * @throws EmployeeNotFoundException if no employee with the given ID is found
     */
    @Override
    public ResponseMessage delete(Long employeeId) {
        employeeRepository.delete(getEmployee(employeeId));
        return new ResponseMessage(EMPLOYEE_DELETED_SUCCESSFULLY);
    }

    private Employee getEmployee(Long employeeId) throws EmployeeNotFoundException {
        return employeeRepository.findById(employeeId).orElseThrow(() -> new EmployeeNotFoundException(String.format(EMPLOYEE_NOT_FOUND, employeeId)));
    }

    private Role getRole(Long employeeId) throws RoleNotFoundException {
        return roleRepository.findById(employeeId).orElseThrow(() -> new RoleNotFoundException(String.format(ROLE_NOT_FOUND, employeeId)));
    }
}
