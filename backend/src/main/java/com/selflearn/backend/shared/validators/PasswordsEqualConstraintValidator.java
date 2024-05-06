package com.selflearn.backend.shared.validators;

import com.selflearn.backend.security.dtos.SignupRequestDto;
import com.selflearn.backend.shared.validators.constraints.PasswordsEqualConstraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordsEqualConstraintValidator implements ConstraintValidator<PasswordsEqualConstraint, Object> {
    @Override
    public void initialize(PasswordsEqualConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Object candidate, ConstraintValidatorContext constraintValidatorContext) {
        SignupRequestDto signupDto = (SignupRequestDto) candidate;
        return signupDto.password().equals(signupDto.confirmPassword());
    }
}
