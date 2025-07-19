package com.archivumlibris.adapter.out.persistence.purchase;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import com.archivumlibris.adapter.out.jpa.purchase.PurchaseRepositoryJPA;
import com.archivumlibris.domain.model.purchase.PayMethod;
import com.archivumlibris.domain.model.purchase.Purchase;
import com.archivumlibris.domain.port.out.purchase.PurchaseRepositoryPort;
import com.archivumlibris.mapper.purchase.PurchaseMapper;

@Repository
public class PurchaseRepositoryImpl implements PurchaseRepositoryPort {

    private final PurchaseRepositoryJPA purchaseRepositoryJPA;

    public PurchaseRepositoryImpl (PurchaseRepositoryJPA purchaseRepositoryJPA) {
        this.purchaseRepositoryJPA = purchaseRepositoryJPA;
    }

    @Override
    public void create(Purchase purchase) {
        this.purchaseRepositoryJPA.save(PurchaseMapper.toEntity(purchase));
    }

    @Override
    public Page<Purchase> findAll(PayMethod payMethod, Long bookId,
            Pageable pageable) {
        return this.purchaseRepositoryJPA.findAll(
            payMethod,
            bookId,
            pageable)
            .map(PurchaseMapper::toDomain);
    }

    @Override
    public Optional<Purchase> findById(Long Id) {
        return this.purchaseRepositoryJPA.findById(Id)
        .map(PurchaseMapper::toDomain);
    }
}
