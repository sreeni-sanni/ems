package com.abn.ems.controller;

import com.abn.ems.model.EmployeeRequest;
import com.abn.ems.model.EmployeeResponse;
import com.abn.ems.service.EmployeeService;
import com.abn.ems.validation.IsValidRole;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/employees")
public class EmployeeController {

    EmployeeService employeeService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<EmployeeResponse> employee(@RequestBody @Valid EmployeeRequest employeeRequest,@RequestHeader("Role") @IsValidRole String role) {
        return ResponseEntity.status(HttpStatus.CREATED).body(employeeService.create(employeeRequest,role));
    }
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping({"/{id}"})
    public ResponseEntity<EmployeeResponse> getEmployee(@PathVariable  @NotEmpty Long id) {
        return ResponseEntity.ok(employeeService.getEmployee(id));
    }
    @PreAuthorize("hasRole('ROLE_USER')")
    @PutMapping ("/{id}")
    public ResponseEntity<EmployeeResponse> updateEmployee(@PathVariable  @NotEmpty Long id,@RequestBody  @Valid EmployeeRequest employeeRequest, @RequestHeader("Role") @IsValidRole String role) {
        return ResponseEntity.ok(employeeService.update(id,employeeRequest));
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable @NotEmpty Long id, @RequestHeader("Role") @IsValidRole String role) {
        return ResponseEntity.ok(employeeService.delete(id));
    }
}
