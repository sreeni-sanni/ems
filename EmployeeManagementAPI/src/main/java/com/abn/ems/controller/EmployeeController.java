package com.abn.ems.controller;

import com.abn.ems.model.EmployeeRequest;
import com.abn.ems.model.EmployeeResponse;
import com.abn.ems.model.ResponseMessage;
import com.abn.ems.service.EmployeeService;
import jakarta.annotation.Nonnull;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.abn.ems.constant.Constant.*;

/**
 * REST controller for managing employee operations.
 *
 * <p>The {@code EmployeeController} provides endpoints for performing CRUD operations
 * on employee data. It allows clients to create, retrieve, update, and delete employees.</p>
 *
 *<p>This controller is secured with role-based access control:
 *  <ul>
 *      <li>ADMIN role: Can create and delete employees.</li>
 *      <li>USER role: Can view and update employee details.</li>
 *  </ul>
 * <h3>Key Features:</h3>
 * <ul>
 *     <li>Create new employee records.</li>
 *     <li>Retrieve employee details by ID .</li>
 *     <li>Update existing employee information by ID.</li>
 *     <li>Delete employees by ID.</li>
 * </ul>
 */

@AllArgsConstructor
@RestController
@RequestMapping(EMPLOYEE_URI_PATH)
public class EmployeeController {

    EmployeeService employeeService;

    /**
     * Creates a new employee.
     *
     * @param employeeRequest the data transfer object containing employee details.
     * @return the created employee as a response entity.
     */
    @PreAuthorize(HAS_ADMIN_ROLE)
    @PostMapping
    public ResponseEntity<EmployeeResponse> employee(@RequestBody @Valid EmployeeRequest employeeRequest,@RequestHeader("Role") String role) {
        return ResponseEntity.status(HttpStatus.CREATED).body(employeeService.create(employeeRequest,role));
    }

    /**
     * Retrieves an employee by ID.
     *
     * @param id the unique identifier of the employee to retrieve.
     * @return the employee details as a response entity.
     */
    @PreAuthorize(HAS_USER_ROLE)
    @GetMapping({"/{id}"})
    public ResponseEntity<EmployeeResponse> getEmployee(@PathVariable  @Nonnull Long id) {
        return ResponseEntity.ok(employeeService.getEmployee(id));
    }

    /**
     * Updates an existing employee by ID.
     *
     * @param id the unique identifier of the employee to update.
     * @param employeeRequest the data transfer object containing updated employee details.
     * @return the updated employee as a response entity.
     */
    @PreAuthorize(HAS_USER_ROLE)
    @PutMapping ("/{id}")
    public ResponseEntity<EmployeeResponse> updateEmployee(@PathVariable  @NotEmpty Long id,@RequestBody  @Valid EmployeeRequest employeeRequest) {
        return ResponseEntity.ok(employeeService.update(id,employeeRequest));
    }

    /**
     * Deletes an employee by ID.
     *
     * @param id the unique identifier of the employee to delete.
     * @return a response  with success message.
     */
    @PreAuthorize(HAS_ADMIN_ROLE)
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseMessage> deleteEmployee(@PathVariable @Nonnull Long id) {
        return ResponseEntity.ok(employeeService.delete(id));
    }
}
