package com.abn.ems.auth;

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
    @PostMapping
    public ResponseEntity<String> getToken(@RequestBody AuthRequest authRequest ) {

        if ("admin".equals(authRequest.getUsername()) && "password".equals(authRequest.getPassword())) {
           String token= jwtUtilService.generateToken(authRequest.getUsername());
           if(jwtUtilService.validateToken(token)) {
              System.out.println("token verified");
           }else {
               System.out.println("token verification failed");
           }
            return ResponseEntity.ok(token);
        }
        throw new RuntimeException("Invalid credentials");
    }
}
