package com.abn.ems.model;

import lombok.Data;

/**
 * Represents the data required for creating or updating an employee.
 * This class is typically used as a request body in API calls.
 * <p>
 * Contains fields for employee attributes such as first name, last name, role, and other details.
 * Validations are included to ensure data integrity and compliance with business rules.
 * </p>
 */
@Data
public class EmployeeDataRequest {

  private String name;
  private Long roleId;

}
