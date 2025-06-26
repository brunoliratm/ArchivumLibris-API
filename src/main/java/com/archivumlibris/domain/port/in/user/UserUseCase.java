package com.archivumlibris.domain.port.in.user;

import java.util.List;
import java.util.Optional;
import com.archivumlibris.dto.request.user.UserPatchRequestDTO;
import com.archivumlibris.dto.request.user.UserRequestDTO;
import com.archivumlibris.dto.response.user.UserResponseDTO;

public interface UserUseCase {

    void create(UserRequestDTO userRequestDTO);

    void update(Long userId, UserPatchRequestDTO userPatchRequestDTO);

    void delete(Long userId);

    Optional<UserResponseDTO> findById(Long userId);

    List<UserResponseDTO> findAll(String name, String email, int page);

}
