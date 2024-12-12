package com.abn.emsdata.service;


import com.abn.emsdata.exception.RoleNotFoundException;

/**
 * Service class for managing roles in the Employee Management System (EMS).
 * <p>
 * This service provides business logic for performing CRUD operations on roles. It interacts with the repository layer
 * to fetch, save, update, and delete role data.
 * </p>
 */
 public interface RoleService {

    /**
     * Deletes an existing role from the system.
     * <p>
     * This method deletes a role identified by its ID from the system. If the role is not found, a
     * {@link com.abn.emsdata.exception.RoleNotFoundException} is thrown.
     * </p>
     *
     * @param roleId the ID of the role to delete
     * @throws com.abn.emsdata.exception.RoleNotFoundException if the role with the given ID does not exist
     */
    String deleteRole(Long roleId) throws RoleNotFoundException;
}
