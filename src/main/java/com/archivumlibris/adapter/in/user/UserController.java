package com.archivumlibris.adapter.in.user;

import com.archivumlibris.domain.port.in.user.UserUseCase;
import com.archivumlibris.dto.request.user.UserPatchRequestDTO;
import com.archivumlibris.dto.request.user.UserRequestDTO;
import com.archivumlibris.dto.response.user.UserResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@Tag(name = "Users", description = "Endpoints for managing users")
public class UserController {

    private final UserUseCase userUseCase;

    public UserController(UserUseCase userUseCase) {
        this.userUseCase = userUseCase;
    }

    @Operation(summary = "Create New User",
            description = "Creates a new user with the provided information",
            responses = {
                    @ApiResponse(responseCode = "201", description = "User created successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid user data",
                            content = @Content(mediaType = "application/json", examples = {
                                    @ExampleObject(name = "Missing Required Fields",
                                            value = "{\"message\": \"name is required\"}"),
                                    @ExampleObject(name = "Invalid Email",
                                            value = "{\"message\": \"Email must be valid\"}")})),
                    @ApiResponse(responseCode = "500", description = "Internal server error",
                            content = @Content(mediaType = "application/json",
                                    examples = @ExampleObject(
                                            value = "{\"message\": \"An unexpected error occurred\"}")))})
    @PostMapping
    public ResponseEntity<Void> create(@RequestBody @Valid UserRequestDTO userRequestDTO) {
        this.userUseCase.create(userRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Get All Users",
            description = "Retrieves a list of users with optional filtering by name and email",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Users retrieved successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid request parameters",
                            content = @Content(mediaType = "application/json",
                                    examples = @ExampleObject(name = "Invalid Pagination",
                                            value = "{\"message\": \"Page index must not be less than zero\"}"))),
                    @ApiResponse(responseCode = "500", description = "Internal server error",
                            content = @Content(mediaType = "application/json",
                                    examples = @ExampleObject(
                                            value = "{\"message\": \"An unexpected error occurred\"}")))})
    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllUsers(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email,
            @RequestParam(required = false, defaultValue = "1") int page) {
        return ResponseEntity.ok(this.userUseCase.findAll(name, email, page));
    }

    @Operation(summary = "Get User by ID",
            description = "Retrieves a user by their unique identifier",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User found successfully"),
                    @ApiResponse(responseCode = "404", description = "User not found",
                            content = @Content(mediaType = "application/json",
                                    examples = @ExampleObject(
                                            value = "{\"message\": \"User not found\"}"))),
                    @ApiResponse(responseCode = "500", description = "Internal server error",
                            content = @Content(mediaType = "application/json",
                                    examples = @ExampleObject(
                                            value = "{\"message\": \"An unexpected error occurred\"}")))})
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long id) {
        return this.userUseCase.findById(id).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Update User",
            description = "Updates an existing user with the provided information (partial updates are allowed)",
            responses = {
                    @ApiResponse(responseCode = "204", description = "User updated successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid user data",
                            content = @Content(mediaType = "application/json",
                                    examples = @ExampleObject(name = "Invalid Email",
                                            value = "{\"message\": \"Email format is invalid. Please provide a valid email address.\"}"))),
                    @ApiResponse(responseCode = "404", description = "User not found",
                            content = @Content(mediaType = "application/json",
                                    examples = @ExampleObject(
                                            value = "{\"message\": \"User not found\"}"))),
                    @ApiResponse(responseCode = "500", description = "Internal server error",
                            content = @Content(mediaType = "application/json",
                                    examples = @ExampleObject(
                                            value = "{\"message\": \"An unexpected error occurred\"}")))})
    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateUser(@PathVariable Long id,
            @RequestBody @Valid UserPatchRequestDTO userPatchRequestDTO) {
        userUseCase.update(id, userPatchRequestDTO);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Delete User", description = "Deletes a user by their unique identifier",
            responses = {
                    @ApiResponse(responseCode = "204", description = "User deleted successfully"),
                    @ApiResponse(responseCode = "404", description = "User not found",
                            content = @Content(mediaType = "application/json",
                                    examples = @ExampleObject(
                                            value = "{\"message\": \"User not found\"}"))),
                    @ApiResponse(responseCode = "500", description = "Internal server error",
                            content = @Content(mediaType = "application/json",
                                    examples = @ExampleObject(
                                            value = "{\"message\": \"An unexpected error occurred\"}")))})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userUseCase.delete(id);
        return ResponseEntity.noContent().build();
    }
}
