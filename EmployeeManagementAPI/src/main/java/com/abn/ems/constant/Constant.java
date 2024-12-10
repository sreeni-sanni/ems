package com.abn.ems.constant;

public abstract class Constant {

    public static final String API_EMPLOYEE = "api/employees";
    public static final String API_EMPLOYEE_BY_ID = "api/employees/{id}";
    public static final String API_EMPLOYEE_BY_NAME = "api/employees/userName/{name}";
    public static final String ERROR_MESSAGE_INVALID_ROLE_UPDATE="Please provide a valid role";
    public static final String INVALID_JWT_TOKEN="Invalid JWT token";
    public static final String TOKEN_EXPIRED="JWT token has expired";
    public static final String INVALID_ROLE="Invalid role provided in the header";
    public static final String RESOURCE_UNAVAILABLE="System is currently unable to process your request due to an issue with a downstream service. Please try again later.";

}

