package com.archivumlibris.dto.response.purchase;

import java.math.BigDecimal;
import com.archivumlibris.domain.model.purchase.PayMethod;
import com.archivumlibris.dto.response.book.BookResponseDTO;
import com.archivumlibris.dto.response.user.UserResponseDTO;

public record PurchaseResponseDTO(
    Long purchaseId,
    UserResponseDTO user,
    BookResponseDTO book,
    PayMethod payMethod,
    BigDecimal price
) {
}
