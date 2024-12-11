package com.abn.emsdata.service;

import com.abn.emsdata.model.EmployeeDataRequest;
import com.abn.emsdata.model.EmployeeDataResponse;
import com.abn.emsdata.model.ResponseMessage;

/**
 * EmployeeService is a service class that handles business logic related to employee data.
 * It provides methods to create, update, retrieve, and delete employee records.
 *
 * <p>It interacts with the Employee repository to perform CRUD operations on Employee entities.
 * This service also handles validation and transformation of employee data before persistence.
 *
 * <p>The service layer is typically used by controllers to interact with the employee data, while also enforcing business rules.
 *
 * <p>All public methods in this class are expected to be transactional and can throw custom exceptions if an operation fails.
 */
public interface EmployeeService {


    /**
     * Creates a new employee.
     *
     * @param employeeDataRequest the employee object to be created
     * @return the created employee object
     */
    EmployeeDataResponse create(EmployeeDataRequest employeeDataRequest);

    /**
     * Retrieves an employee by their ID.
     *
     * @param employeeId the ID of the employee to retrieve
     * @return the employee with the given ID
     */
    EmployeeDataResponse getEmployeeById(Long employeeId);

    /**
     * Retrieves an employee by their surName.
     *
     * @param surName the surName of the employee to retrieve
     * @return the employee with the given surName
     */
    EmployeeDataResponse getEmployeeBySurName(String surName);

    /**
     * Updates an existing employee's details.
     *
     * @param employeeId          the ID of the employee to update
     * @param employeeDataRequest the updated employee details
     * @return the updated employee object
     */
    EmployeeDataResponse update(Long employeeId, EmployeeDataRequest employeeDataRequest);

    /**
     * Deletes an employee by their ID.
     *
     * @param employeeId the ID of the employee to delete
     */
    ResponseMessage delete(Long employeeId);

}
