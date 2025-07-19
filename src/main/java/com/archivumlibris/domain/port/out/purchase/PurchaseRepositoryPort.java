package com.archivumlibris.domain.port.out.purchase;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.archivumlibris.domain.model.purchase.PayMethod;
import com.archivumlibris.domain.model.purchase.Purchase;

public interface PurchaseRepositoryPort {

    void create(Purchase purchase);

    Page<Purchase> findAll (
        PayMethod payMethod,
        Long bookId,
        Pageable pageable
    );

    Optional<Purchase> findById (Long Id);

}
