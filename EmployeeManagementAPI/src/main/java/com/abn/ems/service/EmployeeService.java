package com.abn.ems.service;

import com.abn.ems.model.EmployeeRequest;
import com.abn.ems.model.EmployeeResponse;
import com.abn.ems.model.ResponseMessage;

/**
 * Interface for managing Employee.
 * This interface defines CRUD operations for employees, including creating, updating, retrieving, and deleting employee records.
 * Methods in this interface are designed to handle employee data and return results as per the system requirements.
 * Retry logic can be applied at the implementation level for transient failures.
 */

 public interface EmployeeService {


    /**
     * Retrieves an employee by their ID.
     *
     * @param id the ID of the employee to retrieve
     * @return the employee object
     */
    EmployeeResponse getEmployee(Long id);

    /**
     * Retrieves an employee by their name.
     *
     * @param userName the userName of the employee to retrieve
     * @return the employee object
     */
    EmployeeResponse getUser(String userName);

    /**
     * Creates a new employee in the system.
     *
     * @param employeeRequest the employee object to create
     * @return the newly created EmployeeResponse object
     */
    EmployeeResponse create(EmployeeRequest employeeRequest, String role);

    /**
     * Updates the details of an existing employee.
     *
     * @param id              the ID of the employee to update
     * @param employeeRequest the employee object containing updated details
     * @return the updated employee object
     */

    EmployeeResponse update(Long id, EmployeeRequest employeeRequest);

    /**
     * Deletes an employee by their ID .
     *
     * @param id the ID of the employee to delete
     */

     ResponseMessage delete(Long id);


}
