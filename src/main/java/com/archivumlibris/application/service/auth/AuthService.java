package com.archivumlibris.application.service.auth;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.archivumlibris.application.service.user.UserService;
import com.archivumlibris.domain.model.user.User;
import com.archivumlibris.domain.port.in.auth.AuthUseCase;
import com.archivumlibris.dto.request.auth.AuthRequestDTO;
import com.archivumlibris.dto.request.user.UserRequestDTO;
import com.archivumlibris.exception.user.UserNotFoundException;
import com.archivumlibris.security.jwt.TokenService;
import com.archivumlibris.shared.exception.InvalidDataException;
import java.util.Optional;

@Service
public class AuthService implements AuthUseCase {

    private final UserService userService;
    private final TokenService tokenService;
    private final PasswordEncoder passwordEncoder;

    public AuthService(
        UserService userService,
        TokenService tokenService,
        PasswordEncoder passwordEncoder
    ) {
        this.userService = userService;
        this.tokenService = tokenService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public String registerUser(UserRequestDTO userRequestDTO) {
        this.userService.create(userRequestDTO);
        return login(new AuthRequestDTO(userRequestDTO.email(), userRequestDTO.password()));
    }

    @Override
    public String login(AuthRequestDTO authRequestDTO) {
        validateLogin(authRequestDTO);
        return this.tokenService.createToken(authRequestDTO);
    }

    private void validateLogin(AuthRequestDTO authRequestDTO) {
        try {
            if (authRequestDTO.email() == null || authRequestDTO.password() == null) {
                throw new InvalidDataException("Email and password are required");
            }

            if (!authRequestDTO.email().matches("^[^@]+@[^@]+\\.[^@]+$")) {
                throw new InvalidDataException("Invalid email format");
            }

            Optional<User> user = this.userService.findByEmail(authRequestDTO.email());

            if (user.isEmpty()) {
                throw new UserNotFoundException();
            }

            if (!passwordEncoder.matches(authRequestDTO.password(), user.get().getPassword())) {
                throw new InvalidDataException("Invalid email or password");
            }

        } catch (InvalidDataException e) {
            throw new InvalidDataException(e.getMessage());
        } catch (UserNotFoundException e) {
            throw new UserNotFoundException();
        } catch (Exception e) {
            throw new RuntimeException("Error during login");
        }
    }
}
