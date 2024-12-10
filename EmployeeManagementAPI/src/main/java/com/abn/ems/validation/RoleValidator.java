package com.abn.ems.validation;

import com.abn.ems.Enums.Role;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class RoleValidator implements ConstraintValidator<IsValidRole, String> {
    @Override
    public void initialize(IsValidRole constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value != null && value.length() > 3 && value.length() < 50 && Role.isValidRole(value);
    }
}
