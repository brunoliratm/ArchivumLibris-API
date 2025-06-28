package com.archivumlibris.adapter.in.auth;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.archivumlibris.domain.port.in.auth.AuthUseCase;
import com.archivumlibris.dto.request.auth.AuthRequestDTO;
import com.archivumlibris.dto.request.user.UserRequestDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;


@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "Endpoints for authentication")
public class AuthController {

    private final AuthUseCase authUseCase;

    public AuthController(AuthUseCase authUseCase) {
        this.authUseCase = authUseCase;
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
                .header("Authorization", "Bearer " + this.authUseCase.registerUser(userRequestDTO))
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


}
