package com.selflearn.backend.security.dtos;

import com.selflearn.backend.shared.validators.constraints.PasswordsEqualConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
@PasswordsEqualConstraint(message = "Passwords do not match")
public class SignupDto {
    @Email
    private String email;
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$", message = "Password must contain at least one uppercase letter, one lowercase letter, and one number")
    private String password;
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$", message = "Password must contain at least one uppercase letter, one lowercase letter, and one number")
    private String confirmPassword;
}
