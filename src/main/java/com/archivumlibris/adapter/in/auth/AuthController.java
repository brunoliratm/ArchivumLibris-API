package com.archivumlibris.adapter.in.auth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.archivumlibris.domain.model.user.User;
import com.archivumlibris.domain.port.in.auth.AuthUseCase;
import com.archivumlibris.domain.port.in.user.UserUseCase;
import com.archivumlibris.dto.request.auth.AuthRequestDTO;
import com.archivumlibris.dto.request.user.UserRequestDTO;
import com.archivumlibris.dto.response.user.UserResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "Endpoints for authentication")
public class AuthController {

    private final AuthUseCase authUseCase;
    private final UserUseCase userUseCase;

    public AuthController(AuthUseCase authUseCase, UserUseCase userUseCase) {
        this.authUseCase = authUseCase;
        this.userUseCase = userUseCase;
    }

    @Operation(summary = "Register new user",
            description = "Creates a new user and returns a JWT token in the Authorization header.",
            responses = {@ApiResponse(responseCode = "201",
                    description = "User successfully registered. JWT returned in the Authorization header."),
                    @ApiResponse(responseCode = "400", description = "Invalid registration data",
                            content = @Content(mediaType = "application/json", examples = {
                                    @ExampleObject(name = "Missing required field",
                                            value = "{\"message\": \"name is required\"}"),
                                    @ExampleObject(name = "Invalid email",
                                            value = "{\"message\": \"Invalid Email\"}"),
                                    @ExampleObject(name = "Invalid password",
                                            value = "{\"message\": \"Password requires a minimum of 6 characters\"}"),
                                    @ExampleObject(name = "Validation error",
                                            value = "{\"message\": \"Validation error\", \"errors\": {\"email\": \"Invalid Email\"}}")})),
                    @ApiResponse(responseCode = "500", description = "Internal server error",
                            content = @Content(mediaType = "application/json",
                                    examples = @ExampleObject(
                                            value = "{\"message\": \"An unexpected error occurred\"}")))})
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Valid UserRequestDTO userRequestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .header("Authorization", "Bearer " + this.authUseCase.register(userRequestDTO))
                .build();
    }

    @Operation(summary = "Authenticate user",
            description = "Authenticates a user and returns a JWT token in the Authorization header.",
            responses = {@ApiResponse(responseCode = "204",
                    description = "User successfully authenticated. JWT returned in the Authorization header."),
                    @ApiResponse(responseCode = "400", description = "Invalid credentials",
                            content = @Content(mediaType = "application/json", examples = {
                                    @ExampleObject(name = "Missing email",
                                            value = "{\"message\": \"email is required\"}"),
                                    @ExampleObject(name = "Missing password",
                                            value = "{\"message\": \"password is required\"}"),
                                    @ExampleObject(name = "Invalid email",
                                            value = "{\"message\": \"Invalid Email\"}"),
                                    @ExampleObject(name = "Invalid password",
                                            value = "{\"message\": \"Password requires a minimum of 6 characters\"}"),
                                    @ExampleObject(name = "Validation error",
                                            value = "{\"message\": \"Validation error\", \"errors\": {\"email\": \"Invalid Email\"}}")})),
                    @ApiResponse(responseCode = "500", description = "Internal server error",
                            content = @Content(mediaType = "application/json",
                                    examples = @ExampleObject(
                                            value = "{\"message\": \"An unexpected error occurred\"}")))})
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody @Valid AuthRequestDTO authRequestDTO) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .header("Authorization", "Bearer " + this.authUseCase.login(authRequestDTO))
                .build();
    }

    @Operation(summary = "Get current user info",
            description = "Returns information about the currently authenticated user.",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "User info returned successfully."),
                    @ApiResponse(responseCode = "401",
                            description = "Unauthorized (missing or invalid token)",
                            content = @Content(mediaType = "application/json",
                                    examples = @ExampleObject(
                                            value = "{\"message\": \"Unauthorized access. Authentication required.\"}"))),
                    @ApiResponse(responseCode = "403",
                            description = "Access denied (insufficient permission or invalid token)",
                            content = @Content(mediaType = "application/json",
                                    examples = @ExampleObject(
                                            value = "{\"message\": \"Access denied: you do not have permission to access this resource.\"}"))),
                    @ApiResponse(responseCode = "404", description = "User not found.",
                            content = @Content(mediaType = "application/json",
                                    examples = @ExampleObject(
                                            value = "{\"message\": \"User not found\"}")))})
    @GetMapping("/me")
    public ResponseEntity<UserResponseDTO> userInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication != null ? authentication.getPrincipal() : null;
        if (principal instanceof User user) {
            return this.userUseCase.findById(user.getId()).map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

}
