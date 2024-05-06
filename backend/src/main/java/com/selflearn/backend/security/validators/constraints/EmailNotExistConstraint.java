package com.selflearn.backend.security.validators.constraints;

import com.selflearn.backend.security.validators.EmailNotExistConstraintValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value = {ElementType.FIELD})
@Retention(value = RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = EmailNotExistConstraintValidator.class)
public @interface EmailNotExistConstraint {
    String message() default "Email is not existent or not valid";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
