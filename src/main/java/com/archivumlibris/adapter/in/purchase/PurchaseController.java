package com.archivumlibris.adapter.in.purchase;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.archivumlibris.domain.model.user.User;
import com.archivumlibris.domain.port.in.purchase.PurchaseUseCase;
import com.archivumlibris.dto.request.purchase.PurchaseRequestDTO;
import com.archivumlibris.dto.response.purchase.PurchaseResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;



@RestController
@RequestMapping("/api/purchases")
@Tag(name = "Purchases", description = "Endpoints for purchase management")
public class PurchaseController {

    private final PurchaseUseCase purchaseUseCase;

    public PurchaseController(PurchaseUseCase purchaseUseCase) {
        this.purchaseUseCase = purchaseUseCase;
    }

    @Operation(summary = "Create New Purchase",
        description = "Creates a new purchase with the provided information",
        responses = {
            @ApiResponse(responseCode = "201",
                description = "Purchase created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid purchase data",
                content = @Content(mediaType = "application/json", examples = {
                    @ExampleObject(name = "Missing User ID",
                        value = "{\"message\": \"Validation error\", \"errors\": {\"UserId\": \"User ID is required\"}}"),
                    @ExampleObject(name = "Missing Book ID",
                        value = "{\"message\": \"Validation error\", \"errors\": {\"BookId\": \"Book ID is required\"}}"),
                    @ExampleObject(name = "Invalid Payment Method",
                        value = "{\"message\": \"Validation error\", \"errors\": {\"payMethod\": \"Payment method must be one of: CREDIT_CARD, DEBIT_CARD, PIX, PAYPAL, BOLETO, OTHER\"}}")})),
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
            @ApiResponse(responseCode = "404", description = "Resource not found",
                content = @Content(mediaType = "application/json",
                    examples = {
                        @ExampleObject(name = "User Not Found",
                            value = "{\"message\": \"User not found\"}"),
                        @ExampleObject(name = "Book Not Found",
                            value = "{\"message\": \"Book not found\"}")})),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                content = @Content(mediaType = "application/json",
                    examples = @ExampleObject(
                        value = "{\"message\": \"An unexpected error occurred\"}")))})
    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody PurchaseRequestDTO purchaseRequestDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        this.purchaseUseCase.create(user.getId(), purchaseRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "List Purchases",
            description = "Retrieves a list of purchases with optional filters for payment method and book ID.",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Purchases retrieved successfully."),
                    @ApiResponse(responseCode = "400", description = "Invalid request parameters",
                            content = @Content(mediaType = "application/json", examples = {
                                    @ExampleObject(name = "Invalid payment method",
                                            value = "{\"message\": \"Invalid payment method: INVALID_METHOD...\"}"),
                                    @ExampleObject(name = "Invalid pagination",
                                            value = "{\"message\": \"Page index must not be less than zero\"}")})),
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
    @GetMapping
    public ResponseEntity<List<PurchaseResponseDTO>> getAllPurchases(
            @RequestParam(required = false) String payMethod,
            @RequestParam(required = false) Long bookId,
            @RequestParam(required = false, defaultValue = "1") int page) {
        return ResponseEntity.ok(this.purchaseUseCase.findAll(payMethod, bookId, page));
    }

    @Operation(summary = "Get Purchase by ID",
            description = "Retrieves a purchase by its unique identifier.",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Purchase found successfully."),
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
                    @ApiResponse(responseCode = "404", description = "Purchase not found",
                            content = @Content(mediaType = "application/json",
                                    examples = @ExampleObject(
                                            value = "{\"message\": \"Purchase not found\"}"))),
                    @ApiResponse(responseCode = "500", description = "Internal server error",
                            content = @Content(mediaType = "application/json",
                                    examples = @ExampleObject(
                                            value = "{\"message\": \"An unexpected error occurred\"}")))})
    @GetMapping("/{id}")
    public ResponseEntity<PurchaseResponseDTO> getPurchaseById(@PathVariable Long id) {
        return this.purchaseUseCase.findById(id).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
