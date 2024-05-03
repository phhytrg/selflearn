package com.selflearn.backend.security.dtos;

import com.selflearn.backend.shared.validators.constraints.EmailNotExistConstraint;
import com.selflearn.backend.shared.validators.constraints.PasswordsEqualConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@PasswordsEqualConstraint(message = "Passwords do not match")
public record SignupDto(@NotBlank @Email @EmailNotExistConstraint(message = "Email exists") @Pattern(regexp=".+@.+\\..+", message="Please provide a valid email address") String email,
                        @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$",
                                message = "Password must contain at least one uppercase letter, one lowercase letter, and one number")
                        String password,
                        @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$",
                                message = "Password must contain at least one uppercase letter, one lowercase letter, and one number")
                        String confirmPassword) {
}