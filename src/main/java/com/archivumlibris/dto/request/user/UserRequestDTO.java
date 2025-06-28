package com.archivumlibris.dto.request.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRequestDTO(
    @Size(min = 2, max = 100, message = "Name must be less than 100 characters")
    @NotBlank(message = "name is required")
    String name,

    @Email(message = "Invalid Email")
    @NotBlank(message = "email is required")
    String email,

    @NotBlank(message = "password is required")
    @Size(min = 6, message = "Password requires a minimum of 6 characters")
    String password
) {
}
