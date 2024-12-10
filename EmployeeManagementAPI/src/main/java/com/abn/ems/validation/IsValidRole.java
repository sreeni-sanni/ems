package com.abn.ems.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static com.abn.ems.constant.Constant.INVALID_ROLE;

@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = RoleValidator.class)
public @interface IsValidRole {

    String message() default INVALID_ROLE;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
