package com.archivumlibris.dto.response.user;

import com.archivumlibris.domain.model.user.UserRole;

public record UserResponseDTO (
        Long id,
        String nome,
        String email,
        UserRole role
){
}
