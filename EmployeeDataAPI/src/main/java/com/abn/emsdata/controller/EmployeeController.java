package com.abn.emsdata.controller;

import com.abn.emsdata.exception.EmployeeNotFoundException;
import com.abn.emsdata.exception.RoleNotFoundException;
import com.abn.emsdata.model.EmployeeDataRequest;
import com.abn.emsdata.model.EmployeeDataResponse;
import com.abn.emsdata.model.ResponseMessage;
import com.abn.emsdata.service.EmployeeService;
import jakarta.annotation.Nonnull;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.abn.emsdata.constant.Constant.API_EMPLOYEE;
import static com.abn.emsdata.constant.Constant.USER_NAME;

/**
 * EmployeeController is a REST controller that manages CRUD operations for Employee entities.
 * It provides endpoints to create, retrieve, update, and delete employee records.
 * <p>All endpoints return responses in JSON format.

 */
@AllArgsConstructor
@RestController
@RequestMapping(API_EMPLOYEE)
public class EmployeeController {

    private EmployeeService employeeService;

    /**
     * Creates a new employee.
     *
     * @param employeeDataRequest the employee object to be created
     * @return the created employee object
     * @throws RoleNotFoundException if the provided employee details are invalid
     */

    @PostMapping
    public ResponseEntity<EmployeeDataResponse> employee(@RequestBody @Valid EmployeeDataRequest employeeDataRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(employeeService.create(employeeDataRequest));
    }

    /**
     * Updates an existing employee's details.
     *
     * @param id       the ID of the employee to update
     * @param employeeDataRequest the updated employee details
     * @return the updated employee object
     * @throws EmployeeNotFoundException if no employee with the given ID is found
     */
    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDataResponse> updateEmployee( @PathVariable @Nonnull Long id,@RequestBody @Valid EmployeeDataRequest employeeDataRequest) {
        return ResponseEntity.ok(employeeService.update(id,employeeDataRequest));
    }
    /**
     * Retrieves an employee by their ID.
     *
     * @param id the ID of the employee to retrieve
     * @return the employee with the specified ID
     * @throws EmployeeNotFoundException if no employee with the given ID is found
     */
    @GetMapping("{id}")
    public ResponseEntity<EmployeeDataResponse> getEmployee(@PathVariable @Nonnull Long id) {
        return ResponseEntity.ok(employeeService.getEmployeeById(id));
    }

    /**
     * Retrieves an employee by their surName.
     *
     * @param surName the surName of the employee to retrieve
     * @return the employee with the specified surName
     * @throws EmployeeNotFoundException if no employee with the given surName is found
     */

    @GetMapping(USER_NAME+"/{surName}")
    public ResponseEntity<EmployeeDataResponse> getUser(@PathVariable @NotEmpty  String surName) {
        return ResponseEntity.ok(employeeService.getEmployeeBySurName(surName));
    }
    /**
     * Deletes an employee by their ID.
     *
     * @param id the ID of the employee to delete
     * @return a response entity with success message if successful
     * @throws EmployeeNotFoundException if no employee with the given ID is found
     */
    @DeleteMapping("{id}")
    public ResponseEntity<ResponseMessage> deleteEmployee(@PathVariable @Nonnull Long id) {
        return ResponseEntity.ok(employeeService.delete(id));
    }
}
