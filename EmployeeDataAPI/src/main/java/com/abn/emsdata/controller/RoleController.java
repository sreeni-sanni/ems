package com.abn.emsdata.controller;

import com.abn.emsdata.model.Role;
import com.abn.emsdata.service.RoleService;
import jakarta.annotation.Nonnull;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.abn.emsdata.constant.Constant.API_ROLE;

/**
 * REST controller for managing roles within the Employee Management System (EMS).
 * <p>
 * This controller provides endpoints for creating, updating, retrieving, and deleting roles.
 * </p>
 */
 @AllArgsConstructor
@RestController
@RequestMapping(API_ROLE)
public class RoleController {
    private RoleService roleService;

    /**
     * Deletes an existing role from the system.
     * <p>
     * Only users with the ADMIN role can delete roles.
     * If the role is not found, a {@link com.abn.emsdata.exception.RoleNotFoundException} is thrown.
     * </p>
     *
     * @param roleId the ID of the role to delete
     * @throws com.abn.emsdata.exception.RoleNotFoundException if the role with the given ID does not exist
     */
    @DeleteMapping("/{roleId}")
    public ResponseEntity<String> deleteRole(@PathVariable @Nonnull Long roleId) {

        return ResponseEntity.ok(roleService.deleteRole(roleId));
    }
}
