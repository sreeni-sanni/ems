package com.abn.emsdata.controller;

import com.abn.emsdata.model.Role;
import com.abn.emsdata.service.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/role")
public class RoleController {


    RoleService roleService;

    @GetMapping
    public ResponseEntity<List<Role>> roles() {
        return ResponseEntity.ok(roleService.getRoles());
    }
}
