package com.archivumlibris.dto.request.user;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UserRequestDTO(
    @NotBlank(message = "name is required") String name,
    @Pattern(
        regexp = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$",
        message = "Email must be valid"
    )
    @NotBlank(message = "email is required")String email,
    @NotBlank(message = "password is required")
    @Min(value= 4, message = "Password requires a minimum of 4 characters") String password,
    @Pattern(
        regexp = "^(USER|ADMIN)$",
        message = "Role must be a valid value (ADMIN or USER)"
    )
    @NotBlank(message = "role is required") String role
) {
}
