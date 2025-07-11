package com.archivumlibris.dto.request.purchase;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public record PurchaseRequestDTO(
    @NotNull(message = "User ID is required")
    @Positive(message = "User ID must be greater than 0")
    Long UserId,

    @NotNull(message = "Book ID is required")
    @Positive(message = "Book ID must be greater than 0")
    Long BookId,

    @NotBlank(message = "Payment method is required")
    @Pattern(
        regexp = "^(CREDIT_CARD|DEBIT_CARD|PIX|PAYPAL|BOLETO|OTHER)$",
        message = "Payment method must be one of: CREDIT_CARD, DEBIT_CARD, PIX, PAYPAL, BOLETO, OTHER")
    String payMethod
) {

}
