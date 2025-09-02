package com.archivumlibris.domain.port.in.purchase;

import java.util.List;
import java.util.Optional;
import com.archivumlibris.dto.request.purchase.PurchaseRequestDTO;
import com.archivumlibris.dto.response.purchase.PurchaseResponseDTO;

public interface PurchaseUseCase {

    void create(Long userId, PurchaseRequestDTO purchaseRequestDTO);

    List<PurchaseResponseDTO> findAll(String payMethod, Long bookId, int page);

    Optional<PurchaseResponseDTO> findById(Long Id);
}
