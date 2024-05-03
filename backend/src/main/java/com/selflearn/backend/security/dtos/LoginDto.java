package com.selflearn.backend.security.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record LoginDto(@NotBlank @Email String email,
                       @NotBlank @Size(min = 8, max = 40) @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$",
                               message = "Password must contain at least one uppercase letter, one lowercase letter, and one number")
                       String password) {
}
