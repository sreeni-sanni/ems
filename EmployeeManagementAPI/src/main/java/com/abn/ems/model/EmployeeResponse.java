package com.abn.ems.model;

/**
 * Represents the response data for an employee.
 * This record is used to transfer employee details from the application to the client in response to API calls.
 * <p>
 * Contains fields such as the employee's unique ID,  name, role.
 * This class is typically serialized to JSON or XML for API responses.
 * </p>
 */

public record EmployeeResponse (Long  id,String firstName,String surName, Long roleId){

}
