package com.abn.emsdata.controller;

import com.abn.emsdata.model.Role;
import com.abn.emsdata.service.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class RoleController {
    RoleService roleService;

    public ResponseEntity<String> deleteRole(Role role) {
        roleService.deleteRole(role);

    }
}
