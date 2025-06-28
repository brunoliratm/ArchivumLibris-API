package com.archivumlibris.dto.request.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AuthRequestDTO(
    @Email(message = "Invalid Email")
    @NotBlank(message = "email is required")
    String email,

    @NotBlank(message = "password is required")
    @Size(min = 6, message = "Password requires a minimum of 6 characters")
    String password
) {
}
