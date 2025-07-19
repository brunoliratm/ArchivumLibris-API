package com.archivumlibris.adapter.out.jpa.purchase;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.archivumlibris.domain.model.purchase.PayMethod;

public interface PurchaseRepositoryJPA extends JpaRepository<PurchaseEntity, Long> {

    @Query(
    """
    SELECT p FROM PurchaseEntity p
    WHERE (:payMethod IS NULL OR p.payMethod = :payMethod)
    AND (:bookId IS NULL OR p.book.id = :bookId)
    """
)
    Page<PurchaseEntity> findAll(
        @Param("payMethod") PayMethod payMethod,
        @Param("bookId") Long bookId,
        Pageable pageable)
    ;

}
