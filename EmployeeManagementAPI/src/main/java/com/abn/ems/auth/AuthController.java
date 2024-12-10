package com.abn.ems.auth;

import com.abn.ems.Enums.Role;
import com.abn.ems.model.EmployeeResponse;
import com.abn.ems.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    private JwtUtilService jwtUtilService;

    private EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<String> getToken(@RequestBody @Valid AuthRequest authRequest ) {

        EmployeeResponse employee=employeeService.getUser(authRequest.getUsername());
        if (employee.surName().equalsIgnoreCase(authRequest.getUsername())) {
           String token= jwtUtilService.generateToken(employee.surName(), Role.getRole(employee.roleId()));
            return ResponseEntity.ok(token);
        }
        throw new RuntimeException("Invalid credentials");
    }
}
