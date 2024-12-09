package com.abn.ems.controller;

import com.abn.ems.model.EmployeeRequest;
import com.abn.ems.model.EmployeeResponse;
import com.abn.ems.service.EmployeeService;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/employees")
public class EmployeeController {

    EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<EmployeeResponse> employee(@RequestBody EmployeeRequest employeeRequest,@RequestHeader("role") @Size(min=3,max=50) String role) {
        return ResponseEntity.status(HttpStatus.CREATED).body(employeeService.create(employeeRequest));
    }

    @GetMapping({"/{id}"})
    public ResponseEntity<EmployeeResponse> getEmployee(@PathVariable Long id, @RequestHeader("role") @Size(min=3,max=50) String role) {
        return ResponseEntity.ok(employeeService.getEmployee(id));
    }

    @PutMapping ("/{id}")
    public ResponseEntity<EmployeeResponse> updateEmployee(@PathVariable Long id,@RequestBody EmployeeRequest employeeRequest, @RequestHeader("role")@Size(min=3,max=50) String role) {
        return ResponseEntity.ok(employeeService.update(id,employeeRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Long id,@RequestHeader("role") @Size(min=3,max=50) String role) {
        return ResponseEntity.ok(employeeService.delete(id));
    }
}
