package com.abn.ems.constant;

public abstract class Constant {

    public static final String API_EMPLOYEE = "/api/employees";
    public static final String API_EMPLOYEE_BY_ID = "/api/employees/{id}";
    public static final String API_EMPLOYEE_BY_NAME = "/api/employees/userName/{name}";
    public static final String ERROR_MESSAGE_INVALID_ROLE_UPDATE="Please provide a valid role";
    public static final String INVALID_JWT_TOKEN="Invalid JWT token";
    public static final String TOKEN_EXPIRED="JWT token has expired";
    public static final String INVALID_ROLE="Invalid role provided in the header";
    public static final String RESOURCE_UNAVAILABLE="System is currently unable to process your request due to an issue with a downstream service. Please try again later.";
    public static final String USER_NOT_FOUND="The specified username was not found. Please check the username and try again.";
    public static final String ALGORITHM = "HmacSHA256";
    public static final String ROLES = "roles";
    public static final String TOKEN_MISSING = "Authorization code is missing in the request";
    public static final String AUTHORIZATION = "Authorization";
    public static final String BEARER="Bearer ";
    public static final String ROLE_MISSING="Role is missing in the request header";
    public static final String ROLE = "Role";
    public static final String EMPLOYEE_URI_PATH = "/employees";
    public static final String AUTH_TOKEN = "/auth/token";
    public static final String HAS_ADMIN_ROLE = "hasRole('ROLE_ADMIN')";
    public static final String HAS_USER_ROLE ="hasRole('ROLE_USER')";
    public static final String EXCEPTION_OCCURRED="Exception occurred: {}";
}

