package com.archivumlibris.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.archivumlibris.application.service.auth.AuthService;
import com.archivumlibris.application.service.user.UserService;
import com.archivumlibris.domain.model.user.User;
import com.archivumlibris.dto.request.auth.AuthRequestDTO;
import com.archivumlibris.dto.request.user.UserRequestDTO;
import com.archivumlibris.security.jwt.TokenService;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock
    private UserService userService;

    @Mock
    private TokenService tokenService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private Authentication authentication;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthService authservice;

    private User activeUser;

    @BeforeEach
    void setUp() {
        activeUser = new User();
        activeUser.setId(1L);
        activeUser.setName("Test User");
        activeUser.setEmail("test@example.com");
        activeUser.setPassword("encodedPassword");
    }

    @Test
    void testRegisterReturnsToken() {
        UserRequestDTO userRequestDTO =
                new UserRequestDTO("Test User", "test@example.com", "password123");
        AuthRequestDTO authRequestDTO = new AuthRequestDTO("test@example.com", "password123");

        doNothing().when(userService).create(userRequestDTO);
        when(userService.findByEmail("test@example.com"))
                .thenReturn(java.util.Optional.of(activeUser)); 
        when(passwordEncoder.matches("password123", "encodedPassword")).thenReturn(true);
        when(tokenService.createToken(authRequestDTO)).thenReturn("mockedToken");

        String token = authservice.register(userRequestDTO);

        assertEquals("mockedToken", token);
        verify(userService).create(userRequestDTO);
        verify(tokenService).createToken(authRequestDTO);
    }

    @Test
    void testLoginReturnsToken() {
        AuthRequestDTO authRequestDTO = new AuthRequestDTO("test@example.com", "password123");
        when(tokenService.createToken(authRequestDTO)).thenReturn("mockedToken");
        when(userService.findByEmail("test@example.com"))
                .thenReturn(java.util.Optional.of(activeUser));
        when(passwordEncoder.matches("password123", "encodedPassword")).thenReturn(true);

        String token = authservice.login(authRequestDTO);

        assertEquals("mockedToken", token);
        verify(tokenService).createToken(authRequestDTO);
    }

    @Test
    void testLoginThrowsExceptionForInvalidEmailFormat() {
        AuthRequestDTO authRequestDTO = new AuthRequestDTO("invalid-email", "password123");
        Exception exception =
                assertThrows(com.archivumlibris.shared.exception.InvalidDataException.class, () -> {
                    authservice.login(authRequestDTO);
                });
        assertTrue(exception.getMessage().contains("Invalid email format"));
    }

    @Test
    void testLoginThrowsExceptionForUserNotFound() {
        AuthRequestDTO authRequestDTO = new AuthRequestDTO("notfound@example.com", "password123");
        when(userService.findByEmail("notfound@example.com"))
                .thenReturn(java.util.Optional.empty());
        Exception exception =
                assertThrows(com.archivumlibris.exception.user.UserNotFoundException.class, () -> {
                    authservice.login(authRequestDTO);
                });
        assertTrue(exception.getMessage().contains("User not found"));
    }

    @Test
    void testLoginThrowsExceptionForWrongPassword() {
        AuthRequestDTO authRequestDTO = new AuthRequestDTO("test@example.com", "wrongpassword");
        when(userService.findByEmail("test@example.com"))
                .thenReturn(java.util.Optional.of(activeUser));
        when(passwordEncoder.matches("wrongpassword", "encodedPassword")).thenReturn(false);

        Exception exception =
                assertThrows(com.archivumlibris.shared.exception.InvalidDataException.class, () -> {
                    authservice.login(authRequestDTO);
                });
        assertTrue(exception.getMessage().contains("Invalid email or password"));
    }

    @Test
    void testLoginThrowsExceptionForNullEmailOrPassword() {
        AuthRequestDTO authRequestDTO = new AuthRequestDTO(null, null);
        Exception exception =
                assertThrows(com.archivumlibris.shared.exception.InvalidDataException.class, () -> {
                    authservice.login(authRequestDTO);
                });
        assertTrue(exception.getMessage().contains("Email and password are required"));
    }
}
