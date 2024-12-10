package com.abn.ems.Enums;

import lombok.Getter;

@Getter
public enum Role {

    ADMIN("ADMIN", 1L),
    USER("USER", 2L),
    MANAGER("MANAGER", 3L);
    private final String role;
    private final Long id;

    Role(String role, Long id) {
        this.role = role;
        this.id = id;
    }


    public static boolean isValidRole(String roleName) {
        for (Role role : Role.values()) {
            if (role.role.equalsIgnoreCase(roleName)) {
                return true;
            }
        }
        return false;
    }
    public static String getRole(Long id) {
        for (Role role : Role.values()) {
            if (role.getId().equals(id)) {
                return role.getRole();
            }
        }
        return null;
    }

    public static Long getRoleId(String roleName) {
        for (Role role : Role.values()) {
            if (role.role.equalsIgnoreCase(roleName)) {
                return role.getId();
            }
        }
        return null;
    }

}
