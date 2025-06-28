package com.archivumlibris.mapper.user;

import com.archivumlibris.domain.model.user.User;
import com.archivumlibris.dto.request.user.UserPatchRequestDTO;
import com.archivumlibris.dto.request.user.UserRequestDTO;
import com.archivumlibris.dto.response.user.UserResponseDTO;

public class UserDTOMapper {

    public static User toModel(UserRequestDTO dto) {
        User user = new User();
        user.setName(dto.name());
        user.setEmail(dto.email());
        user.setPassword(dto.password());
        return user;
    }

    public static User toModel(UserPatchRequestDTO dto) {
        User user = new User();
        user.setName(dto.name());
        user.setEmail(dto.email());
        user.setPassword(dto.password());
        return user;
    }

    public static UserResponseDTO toResponseDTO(User user) {
        return new UserResponseDTO(
            user.getId(),
            user.getName(),
            user.getEmail(),
            user.getRole()
        );
    }
}
