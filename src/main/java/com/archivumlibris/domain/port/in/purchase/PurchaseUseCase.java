package com.archivumlibris.domain.port.in.purchase;

import java.util.List;
import java.util.Optional;
import com.archivumlibris.domain.model.purchase.PayMethod;
import com.archivumlibris.dto.response.purchase.PurchaseResponseDTO;

public interface PurchaseUseCase {

    void create();
    
    List<PurchaseResponseDTO> findAll(Long id, PayMethod payMethod);

    Optional<PurchaseResponseDTO> findById(Long Id);
}
