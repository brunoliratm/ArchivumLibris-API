package com.archivumlibris.book.adapter.in;

import com.archivumlibris.book.domain.model.Book;
import com.archivumlibris.book.domain.port.in.BookUseCase;
import com.archivumlibris.book.dto.request.BookRequestDTO;
import com.archivumlibris.book.dto.response.BookResponseDTO;
import com.archivumlibris.book.mapper.BookDTOMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
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
@Tag(name = "Books", description = "Endpoints for managing books")
public class BookController {

    private final BookUseCase bookUseCase;

    public BookController(BookUseCase bookUseCase) {
        this.bookUseCase = bookUseCase;
    }

    @Operation(
        summary = "Create New Book",
        description = "Creates a new book with the provided information",
        responses = {
            @ApiResponse(
                responseCode = "201",
                description = "Book created successfully"
            ),
            @ApiResponse(
                responseCode = "400",
                description = "Invalid book data",
                content = @Content(
                    mediaType = "application/json",
                    examples = {
                        @ExampleObject(
                            name = "Missing Required Fields",
                            value = "{\"message\": \"Title is required\"}"
                        ),
                        @ExampleObject(
                            name = "Invalid Price",
                            value = "{\"message\": \"Price must be positive\"}"
                        ),
                        @ExampleObject(
                            name = "Invalid Genre",
                            value = "{\"message\": \"Genre must be a valid BookGenre enum value\"}"
                        ),
                    }
                )
            ),
            @ApiResponse(
                responseCode = "500",
                description = "Internal server error",
                content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(
                        value = "{\"message\": \"An unexpected error occurred\"}"
                    )
                )
            ),
        }
    )
    @PostMapping
    public ResponseEntity<Void> create(
        @RequestBody @Valid BookRequestDTO bookRequestDTO
    ) {
        Book book = BookDTOMapper.toModel(bookRequestDTO);
        this.bookUseCase.create(book);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(
        summary = "Get All Books",
        description = "Retrieves a list of books with optional filtering by genre, title, publisher, and author",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Books retrieved successfully"
            ),
            @ApiResponse(
                responseCode = "400",
                description = "Invalid request parameters",
                content = @Content(
                    mediaType = "application/json",
                    examples = {
                        @ExampleObject(
                            name = "Invalid Genre",
                            value = "{\"message\": \"Invalid genre: INVALID_GENRE\"}"
                        ),
                        @ExampleObject(
                            name = "Invalid Pagination",
                            value = "{\"message\": \"Page index must not be less than zero\"}"
                        ),
                    }
                )
            ),
            @ApiResponse(
                responseCode = "500",
                description = "Internal server error",
                content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(
                        value = "{\"message\": \"An unexpected error occurred\"}"
                    )
                )
            ),
        }
    )
    @GetMapping
    public ResponseEntity<List<BookResponseDTO>> getAllBooks(
        @RequestParam(required = false) String genre,
        @RequestParam(required = false) String title,
        @RequestParam(required = false) String publisher,
        @RequestParam(required = false) String author,
        @RequestParam(required = false, defaultValue = "1") int page
    ) {
        return ResponseEntity.ok(
            this.bookUseCase.findAllBooks(genre, title, publisher, author, page)
                .stream()
                .map(BookDTOMapper::toResponseDTO)
                .collect(Collectors.toList())
        );
    }

    @Operation(
        summary = "Get Book by ID",
        description = "Retrieves a book by its unique identifier",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Book found successfully"
            ),
            @ApiResponse(
                responseCode = "404",
                description = "Book not found",
                content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(
                        value = "{\"message\": \"Book not found\"}"
                    )
                )
            ),
            @ApiResponse(
                responseCode = "500",
                description = "Internal server error",
                content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(
                        value = "{\"message\": \"An unexpected error occurred\"}"
                    )
                )
            ),
        }
    )
    @GetMapping("/{id}")
    public ResponseEntity<BookResponseDTO> getBookById(@PathVariable Long id) {
        return this.bookUseCase.findById(id)
            .map(BookDTOMapper::toResponseDTO)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
        summary = "Update Book",
        description = "Updates an existing book with the provided information",
        responses = {
            @ApiResponse(
                responseCode = "204",
                description = "Book updated successfully"
            ),
            @ApiResponse(
                responseCode = "400",
                description = "Invalid book data",
                content = @Content(
                    mediaType = "application/json",
                    examples = {
                        @ExampleObject(
                            name = "Missing Required Fields",
                            value = "{\"message\": \"Title is required\"}"
                        ),
                        @ExampleObject(
                            name = "Invalid Price",
                            value = "{\"message\": \"Price must be positive\"}"
                        ),
                        @ExampleObject(
                            name = "Invalid Genre",
                            value = "{\"message\": \"Genre must be a valid BookGenre enum value\"}"
                        ),
                    }
                )
            ),
            @ApiResponse(
                responseCode = "404",
                description = "Book not found",
                content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(
                        value = "{\"message\": \"Book not found\"}"
                    )
                )
            ),
            @ApiResponse(
                responseCode = "500",
                description = "Internal server error",
                content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(
                        value = "{\"message\": \"An unexpected error occurred\"}"
                    )
                )
            ),
        }
    )
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateBook(
        @PathVariable Long id,
        @RequestBody @Valid BookRequestDTO bookRequestDTO
    ) {
        Book book = BookDTOMapper.toModel(bookRequestDTO);
        bookUseCase.update(id, book);
        return ResponseEntity.noContent().build();
    }

    @Operation(
        summary = "Delete Book",
        description = "Deletes a book by its unique identifier",
        responses = {
            @ApiResponse(
                responseCode = "204",
                description = "Book deleted successfully"
            ),
            @ApiResponse(
                responseCode = "404",
                description = "Book not found",
                content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(
                        value = "{\"message\": \"Book not found\"}"
                    )
                )
            ),
            @ApiResponse(
                responseCode = "500",
                description = "Internal server error",
                content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(
                        value = "{\"message\": \"An unexpected error occurred\"}"
                    )
                )
            ),
        }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookUseCase.delete(id);
        return ResponseEntity.noContent().build();
    }
}
