package com.selflearn.backend.security.validators;

import com.selflearn.backend.security.validators.constraints.EmailNotExistConstraint;
import com.selflearn.backend.user.models.User;
import com.selflearn.backend.user.services.UserService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class EmailNotExistConstraintValidator implements ConstraintValidator<EmailNotExistConstraint, String> {
    private final UserService userService;

    @Override
    public void initialize(EmailNotExistConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        User user = userService.fetchUserByEmail(s);
        if (user != null) {
            return false;
        }
        return true;
    }
}
