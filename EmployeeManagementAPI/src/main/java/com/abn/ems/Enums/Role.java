package com.abn.ems.Enums;

import com.abn.ems.exception.RoleNotFoundException;
import lombok.Getter;

import static com.abn.ems.constant.Constant.ROLE_NOT_FOUND;

/**
 * Enum representing the various roles available in the application.
 *
 * <p>The {@code Role} enum is used to define the roles assigned to users within the system.
 * These roles are typically used for access control and authorization, determining what actions
 * a user is permitted to perform.</p>
 *
 * <h3>Roles:</h3>
 * <ul>
 *     <li>{@link #ADMIN}: Represents an administrative user with full access.</li>
 *     <li>{@link #USER}: Represents a regular user with limited access to resources.</li>
 *     <li>{@link #MANAGER}: Represents a guest user with minimal access, usually read-only.</li>
 * </ul>
 */
@Getter
public enum Role {

    ADMIN("ADMIN", 1L), USER("USER", 2L), MANAGER("MANAGER", 3L);
    private final String role;
    private final Long id;

    Role(String role, Long id) {
        this.role = role;
        this.id = id;
    }

    /**
     * Converts a string representation of a role to the corresponding {@code Role} enum value.
     *
     * @param role the string representation of the role to check.
     * @return the corresponding {@code Role} enum value.
     * @throws com.abn.ems.exception.RoleNotFoundException if the input does not match any role.
     */
    public static boolean isValidRole(String role) {
        for (Role r : Role.values()) {
            if(r.role.equalsIgnoreCase(role)){
                return true;
            }
        }
        return false;
    }

    /**
     * Converts a string representation of a role to the corresponding {@code Role} enum value.
     *
     * @param id the Long representation of the role (e.g., "ADMIN").
     * @return the corresponding {@code Role} enum value.
     * @throws com.abn.ems.exception.RoleNotFoundException if the input does not match any role.
     */
    public static String getRole(Long id) {
        for (Role role : Role.values()) {
            if (role.getId().equals(id)) {
                return role.getRole();
            }
        }
        throw new RoleNotFoundException(String.format(ROLE_NOT_FOUND,id));
    }

    /**
     * Converts a string representation of a role to the corresponding {@code Role} enum value.
     *
     * @param role the Long representation of the role (e.g., "ADMIN").
     * @return the corresponding {@code Role} enum value.
     * @throws com.abn.ems.exception.RoleNotFoundException if the input does not match any role.
     */
    public static Long getRoleId(String role) {
        for (Role r : Role.values()) {
            if (r.role.equalsIgnoreCase(role)) {
                return r.getId();
            }
        }
        throw new RoleNotFoundException(String.format(ROLE_NOT_FOUND,role));
    }

}
