package com.abn.ems.model;

import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.NotEmpty;

/**
 * Represents the request data for creating or updating an employee.
 * This record is typically used as a request body in API calls for employee-related operations.
 * <p>
 * It contains fields for employee details such as first name, last name, and role.
 * The record ensures immutability and provides built-in support for equals, hashCode, and toString.
 * </p>
 */
 public record EmployeeRequest(@NotEmpty String firstName, @NotEmpty String surName, @Nonnull Long roleId) {
}
