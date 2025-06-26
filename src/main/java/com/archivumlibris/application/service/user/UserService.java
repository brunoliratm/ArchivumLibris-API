package com.archivumlibris.application.service.user;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.archivumlibris.domain.model.user.User;
import com.archivumlibris.domain.model.user.UserRole;
import com.archivumlibris.domain.port.in.user.UserUseCase;
import com.archivumlibris.domain.port.out.user.UserRepositoryPort;
import com.archivumlibris.dto.request.user.UserPatchRequestDTO;
import com.archivumlibris.dto.request.user.UserRequestDTO;
import com.archivumlibris.dto.response.user.UserResponseDTO;
import com.archivumlibris.exception.user.UserNotFoundException;
import com.archivumlibris.mapper.user.UserDTOMapper;
import com.archivumlibris.shared.exception.InvalidDataException;
import com.archivumlibris.shared.exception.InvalidPageException;

@Service
@Transactional
public class UserService implements UserUseCase {

    private final UserRepositoryPort userRepositoryPort;

    public UserService(UserRepositoryPort userRepositoryPort) {
        this.userRepositoryPort = userRepositoryPort;
    }

    @Override
    public void create(UserRequestDTO userRequestDTO) {
        userRepositoryPort.findByEmail(userRequestDTO.email()).ifPresent(u -> {
            throw new InvalidDataException("Email already in use");
        });
        User user = UserDTOMapper.toModel(userRequestDTO);
        validateUserData(user);
        this.userRepositoryPort.save(user);
    }

    @Override
    public void update(Long userId, UserPatchRequestDTO userPatchRequestDTO) {
        User existingUser =
                this.userRepositoryPort.findById(userId).orElseThrow(UserNotFoundException::new);

        User user = UserDTOMapper.toModel(userPatchRequestDTO);
        if (user.getName() != null && !user.getName().trim().isEmpty()) {
            existingUser.setName(user.getName());
        }
        if (userPatchRequestDTO.email() != null && !userPatchRequestDTO.email().trim().isEmpty()
                && !userPatchRequestDTO.email().equals(existingUser.getEmail())) {
            userRepositoryPort.findByEmail(userPatchRequestDTO.email()).ifPresent(u -> {
                if (!u.getId().equals(userId)) {
                    throw new InvalidDataException("Email already in use by another user");
                }
            });
            existingUser.setEmail(userPatchRequestDTO.email());
        }
        if (user.getPassword() != null && !user.getPassword().trim().isEmpty()) {
            existingUser.setPassword(user.getPassword());
        }
        if (user.getRole() != null) {
            try {
                existingUser.setRole(UserRole.valueOf(userPatchRequestDTO.role()));
            } catch (IllegalArgumentException e) {
                throw new InvalidDataException("Invalid role: " + userPatchRequestDTO.role());
            }
        }

        this.userRepositoryPort.save(existingUser);
    }

    @Override
    public void delete(Long userId) {
        if (!this.userRepositoryPort.findById(userId).isPresent()) {
            throw new UserNotFoundException();
        }
        this.userRepositoryPort.delete(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserResponseDTO> findById(Long userId) {
        Optional<User> user = this.userRepositoryPort.findById(userId);
        if (!user.isPresent()) {
            throw new UserNotFoundException();
        }
        return user.map(UserDTOMapper::toResponseDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserResponseDTO> findAll(String name, String email, int page) {
        if (page < 1) {
            throw new InvalidPageException();
        }

        int pageIndex = page - 1;
        Pageable pageable = PageRequest.of(pageIndex, 10);

        Page<User> users = this.userRepositoryPort.findAll(name, email, pageable);
        return users.getContent().stream().map(UserDTOMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    private void validateUserData(User user) {
        if (user.getName() == null || user.getName().trim().isEmpty()) {
            throw new InvalidDataException("User name cannot be empty");
        }
        if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            throw new InvalidDataException("User email cannot be empty");
        }
        if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
            throw new InvalidDataException("User password cannot be empty");
        }
        if (user.getRole() == null) {
            throw new InvalidDataException("User role cannot be null");
        }
    }

}
