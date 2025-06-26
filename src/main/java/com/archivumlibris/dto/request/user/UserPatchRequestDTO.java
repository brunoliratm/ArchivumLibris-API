package com.archivumlibris.dto.request.user;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;

public record UserPatchRequestDTO(
    @Max(value=100, message = "Name must be less than 100 characters")
    String name,
    @Pattern(
        regexp = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$",
        message = "Email format is invalid. Please provide a valid email address."
    )
    String email,
    @Min(value= 4, message = "Password requires a minimum of 4 characters") String password,
    @Pattern(
        regexp = "^(USER|ADMIN)$",
        message = "Role must be a valid value (ADMIN or USER)"
    )
    String role
) {
}
