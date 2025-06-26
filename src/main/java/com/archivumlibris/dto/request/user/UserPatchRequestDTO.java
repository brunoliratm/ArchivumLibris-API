package com.archivumlibris.dto.request.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserPatchRequestDTO(
    @Size(min = 2, max = 100, message = "Name must be less than 100 characters")
    String name,

    @Email(message = "Invalid Email")
    String email,

    @Size(min = 6, message = "Password requires a minimum of 6 characters")
    String password,

    @Pattern(
        regexp = "^(USER|ADMIN)$",
        message = "Role must be a valid value (ADMIN or USER)"
    )
    String role
) {
}
