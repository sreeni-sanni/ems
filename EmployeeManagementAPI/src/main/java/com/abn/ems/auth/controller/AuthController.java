package com.abn.ems.auth.controller;

import com.abn.ems.auth.model.AuthRequest;
import com.abn.ems.auth.service.AuthServiceImpl;
import com.abn.ems.auth.service.JwtUtilService;
import com.abn.ems.enums.Role;
import com.abn.ems.exception.EmsApplicationException;
import com.abn.ems.model.EmployeeResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.abn.ems.constants.Constant.AUTH_TOKEN;
import static com.abn.ems.constants.Constant.USER_NOT_FOUND;

@Slf4j
@AllArgsConstructor
@RestController
public class AuthController {

    private JwtUtilService jwtUtilService;

    private AuthServiceImpl authServiceImpl;

    /**
     *  * Generates a JWT token for the given username.
     *  <p>This method accepts a username to generate a JSON Web Token (JWT).
     * The token is signed using the application's secret key and includes an expiration time.
     * The generated token can be used for authentication and authorization purposes.</p>
     *
     * @param authRequest the authRequest for which the token is to be generated.
     *                    Must not be null or empty.
     * @return token
     */
    @PostMapping(AUTH_TOKEN)
    public ResponseEntity<String> getToken(@RequestBody @Valid AuthRequest authRequest ) {

        EmployeeResponse employee=authServiceImpl.getEmployee(authRequest.username());
        if (employee.surName().equalsIgnoreCase(authRequest.username())) {
           String token= jwtUtilService.generateToken(employee.surName(), Role.getRole(employee.roleId()));
            return ResponseEntity.ok(token);
        }
        throw new EmsApplicationException(USER_NOT_FOUND);
    }
}
