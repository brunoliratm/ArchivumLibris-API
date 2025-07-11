package com.archivumlibris.adapter.out.jpa.purchase;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseRepositoryJPA extends JpaRepository<PurchaseEntity, Long> {

    Page<PurchaseEntity> findAllByPayMethodContainingIgnoreCaseAndBookIdAndUserId(
        String payMethod,
        Long bookId,
        Long userId,
        Pageable pageable)
    ;

}
