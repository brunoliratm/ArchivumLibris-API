package com.archivumlibris.services;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.archivumlibris.application.service.user.UserService;
import com.archivumlibris.domain.model.user.User;
import com.archivumlibris.domain.model.user.UserRole;
import com.archivumlibris.domain.port.out.user.UserRepositoryPort;
import com.archivumlibris.dto.request.user.UserPatchRequestDTO;
import com.archivumlibris.dto.request.user.UserRequestDTO;
import com.archivumlibris.dto.response.user.UserResponseDTO;
import com.archivumlibris.exception.user.UserNotFoundException;
import com.archivumlibris.shared.exception.InvalidDataException;
import com.archivumlibris.shared.exception.InvalidPageException;

class UserServiceTest {

    @Mock
    private UserRepositoryPort userRepositoryPort;
    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User(1L, "User", "user@email.com", "pass", UserRole.USER, false);
    }

    @Test
    void testCreateUserSuccess() {
        UserRequestDTO dto = new UserRequestDTO("User", "user@email.com", "pass123");
        when(userRepositoryPort.findByEmail("user@email.com")).thenReturn(Optional.empty());
        when(passwordEncoder.encode(anyString())).thenReturn("encoded");
        assertDoesNotThrow(() -> userService.create(dto));
    }

    @Test
    void testCreateUserEmailAlreadyExists() {
        UserRequestDTO dto = new UserRequestDTO("User", "user@email.com", "pass123");
        when(userRepositoryPort.findByEmail("user@email.com")).thenReturn(Optional.of(user));
        assertThrows(InvalidDataException.class, () -> userService.create(dto));
    }

    @Test
    void testUpdateUserSuccess() {
        UserPatchRequestDTO dto = new UserPatchRequestDTO("NewName", "new@email.com", "newpass");
        when(userRepositoryPort.findById(1L)).thenReturn(Optional.of(user));
        when(userRepositoryPort.findByEmail("new@email.com")).thenReturn(Optional.empty());
        when(passwordEncoder.encode(anyString())).thenReturn("encoded");
        assertDoesNotThrow(() -> userService.update(1L, dto));
    }

    @Test
    void testUpdateUserNotFound() {
        UserPatchRequestDTO dto = new UserPatchRequestDTO("NewName", "new@email.com", "newpass");
        when(userRepositoryPort.findById(1L)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userService.update(1L, dto));
    }

    @Test
    void testDeleteUserSuccess() {
        when(userRepositoryPort.findById(1L)).thenReturn(Optional.of(user));
        assertDoesNotThrow(() -> userService.delete(1L));
    }

    @Test
    void testDeleteUserNotFound() {
        when(userRepositoryPort.findById(1L)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userService.delete(1L));
    }

    @Test
    void testFindByIdSuccess() {
        when(userRepositoryPort.findById(1L)).thenReturn(Optional.of(user));
        Optional<UserResponseDTO> result = userService.findById(1L);
        assertTrue(result.isPresent());
        assertEquals("User", result.get().name());
    }

    @Test
    void testFindByIdNotFound() {
        when(userRepositoryPort.findById(1L)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userService.findById(1L));
    }

    @Test
    void testFindAllSuccess() {
        Page<User> page = new PageImpl<>(List.of(user));
        when(userRepositoryPort.findAll(any(), any(), any(Pageable.class))).thenReturn(page);
        List<UserResponseDTO> result = userService.findAll("User", "user@email.com", 1);
        assertEquals(1, result.size());
    }

    @Test
    void testFindAllInvalidPage() {
        assertThrows(InvalidPageException.class,
                () -> userService.findAll("User", "user@email.com", 0));
    }

    @Test
    void testLoadUserByEmailSuccess() {
        when(userRepositoryPort.findByEmail("user@email.com")).thenReturn(Optional.of(user));
        assertNotNull(userService.loadUserByEmail("user@email.com"));
    }

    @Test
    void testLoadUserByEmailNotFound() {
        when(userRepositoryPort.findByEmail("user@email.com")).thenReturn(Optional.empty());
        assertThrows(UsernameNotFoundException.class,
                () -> userService.loadUserByEmail("user@email.com"));
    }
}
