package com.abn.emsdata.controller;

import com.abn.emsdata.exception.RoleNotFoundException;
import com.abn.emsdata.model.EmployeeDataRequest;
import com.abn.emsdata.model.EmployeeDataResponse;
import com.abn.emsdata.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<EmployeeDataResponse> employee(@RequestBody EmployeeDataRequest employeeDataRequest) throws RoleNotFoundException {
        return ResponseEntity.status(HttpStatus.CREATED).body(employeeService.create(employeeDataRequest));
    }
    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDataResponse> updateEmployee( @PathVariable Long id,@RequestBody EmployeeDataRequest employeeDataRequest) {
        return ResponseEntity.ok(employeeService.update(id,employeeDataRequest));
    }

    @GetMapping("{id}")
    public ResponseEntity<EmployeeDataResponse> getEmployee(@PathVariable Long id) {
        return ResponseEntity.ok(employeeService.getEmployeeById(id));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Long id) {
        return ResponseEntity.ok(employeeService.delete(id));
    }
}
