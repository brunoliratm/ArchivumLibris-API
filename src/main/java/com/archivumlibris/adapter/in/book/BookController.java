package com.archivumlibris.adapter.in.book;

import com.archivumlibris.domain.port.in.book.BookUseCase;
import com.archivumlibris.dto.request.book.BookRequestDTO;
import com.archivumlibris.dto.response.book.BookResponseDTO;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/books")
@Tag(name = "Books",
        description = "Endpoints for book management.")
public class BookController {

    private final BookUseCase bookUseCase;

    public BookController(BookUseCase bookUseCase) {
        this.bookUseCase = bookUseCase;
    }

    @Operation(summary = "Create new book",
            description = "Creates a new book with the provided information.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Book created successfully."),
                    @ApiResponse(responseCode = "400", description = "Invalid book data",
                            content = @Content(mediaType = "application/json", examples = {
                                    @ExampleObject(name = "Missing required field",
                                            value = "{\"message\": \"Title is required\"}"),
                                    @ExampleObject(name = "Invalid price",
                                            value = "{\"message\": \"Price must be positive\"}"),
                                    @ExampleObject(name = "Invalid genre",
                                            value = "{\"message\": \"Genre must be a valid BookGenre enum value\"}"),
                                    @ExampleObject(name = "Validation error",
                                            value = "{\"message\": \"Validation error\", \"errors\": {\"title\": \"Title is required\"}}")})),
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
                    @ApiResponse(responseCode = "500", description = "Internal server error",
                            content = @Content(mediaType = "application/json",
                                    examples = @ExampleObject(
                                            value = "{\"message\": \"An unexpected error occurred\"}")))})
    @PostMapping
    public ResponseEntity<Void> create(@RequestBody @Valid BookRequestDTO bookRequestDTO) {
        this.bookUseCase.create(bookRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "List books",
            description = "Retrieves a list of books with optional filters for genre, title, publisher, and author.",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Books retrieved successfully."),
                    @ApiResponse(responseCode = "400", description = "Invalid request parameters",
                            content = @Content(mediaType = "application/json", examples = {
                                    @ExampleObject(name = "Invalid genre",
                                            value = "{\"message\": \"Invalid genre: INVALID_GENRE\"}"),
                                    @ExampleObject(name = "Invalid pagination",
                                            value = "{\"message\": \"Page index must not be less than zero\"}")})),
                    @ApiResponse(responseCode = "500", description = "Internal server error",
                            content = @Content(mediaType = "application/json",
                                    examples = @ExampleObject(
                                            value = "{\"message\": \"An unexpected error occurred\"}")))})
    @GetMapping
    public ResponseEntity<List<BookResponseDTO>> getAllBooks(
            @RequestParam(required = false) String genre,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String publisher,
            @RequestParam(required = false) String author,
            @RequestParam(required = false, defaultValue = "1") int page) {
        return ResponseEntity.ok(this.bookUseCase.findAll(genre, title, publisher, author, page));
    }

    @Operation(summary = "Get book by ID",
            description = "Retrieves a book by its unique identifier.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Book found successfully."),
                    @ApiResponse(responseCode = "404", description = "Book not found",
                            content = @Content(mediaType = "application/json",
                                    examples = @ExampleObject(
                                            value = "{\"message\": \"Book not found\"}"))),
                    @ApiResponse(responseCode = "500", description = "Internal server error",
                            content = @Content(mediaType = "application/json",
                                    examples = @ExampleObject(
                                            value = "{\"message\": \"An unexpected error occurred\"}")))})
    @GetMapping("/{id}")
    public ResponseEntity<BookResponseDTO> getBookById(@PathVariable Long id) {
        return this.bookUseCase.findById(id).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Update book",
            description = "Updates an existing book with the provided information.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Book updated successfully."),
                    @ApiResponse(responseCode = "400", description = "Invalid book data",
                            content = @Content(mediaType = "application/json", examples = {
                                    @ExampleObject(name = "Missing required field",
                                            value = "{\"message\": \"Title is required\"}"),
                                    @ExampleObject(name = "Invalid price",
                                            value = "{\"message\": \"Price must be positive\"}"),
                                    @ExampleObject(name = "Invalid genre",
                                            value = "{\"message\": \"Genre must be a valid BookGenre enum value\"}"),
                                    @ExampleObject(name = "Validation error",
                                            value = "{\"message\": \"Validation error\", \"errors\": {\"title\": \"Title is required\"}}")})),
                    @ApiResponse(responseCode = "404", description = "Book not found",
                            content = @Content(mediaType = "application/json",
                                    examples = @ExampleObject(
                                            value = "{\"message\": \"Book not found\"}"))),
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
                    @ApiResponse(responseCode = "500", description = "Internal server error",
                            content = @Content(mediaType = "application/json",
                                    examples = @ExampleObject(
                                            value = "{\"message\": \"An unexpected error occurred\"}")))})
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateBook(@PathVariable Long id,
            @RequestBody @Valid BookRequestDTO bookRequestDTO) {
        bookUseCase.update(id, bookRequestDTO);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Delete book", description = "Removes a book by its unique identifier.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Book deleted successfully."),
                    @ApiResponse(responseCode = "404", description = "Book not found",
                            content = @Content(mediaType = "application/json",
                                    examples = @ExampleObject(
                                            value = "{\"message\": \"Book not found\"}"))),
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
                    @ApiResponse(responseCode = "500", description = "Internal server error",
                            content = @Content(mediaType = "application/json",
                                    examples = @ExampleObject(
                                            value = "{\"message\": \"An unexpected error occurred\"}")))})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookUseCase.delete(id);
        return ResponseEntity.noContent().build();
    }
}
