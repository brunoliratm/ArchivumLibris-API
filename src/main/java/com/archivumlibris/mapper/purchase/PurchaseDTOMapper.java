package com.archivumlibris.mapper.purchase;

import com.archivumlibris.domain.model.purchase.Purchase;
import com.archivumlibris.dto.response.purchase.PurchaseResponseDTO;
import com.archivumlibris.mapper.book.BookDTOMapper;
import com.archivumlibris.mapper.user.UserDTOMapper;

public class PurchaseDTOMapper {

    public static PurchaseResponseDTO toResponseDTO(Purchase purchase) {
        return new PurchaseResponseDTO(
            purchase.getId(),
            UserDTOMapper.toResponseDTO(purchase.getUser()),
            BookDTOMapper.toResponseDTO(purchase.getBook()),
            purchase.getPayMethod(),
            purchase.getPrice()
        );
    }

}
