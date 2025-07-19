package com.archivumlibris.mapper.purchase;

import com.archivumlibris.adapter.out.jpa.purchase.PurchaseEntity;
import com.archivumlibris.domain.model.purchase.Purchase;
import com.archivumlibris.mapper.book.BookMapper;
import com.archivumlibris.mapper.user.UserMapper;

public class PurchaseMapper {

        public static PurchaseEntity toEntity(Purchase purchase) {
        PurchaseEntity entity = new PurchaseEntity();
        entity.setId(purchase.getId());
        entity.setPurchaseDate(purchase.getPurchaseDate());
        entity.setPayMethod(purchase.getPayMethod());
        entity.setPrice(purchase.getPrice());
        entity.setBook(BookMapper.toEntity(purchase.getBook()));
        entity.setUser(UserMapper.toEntity(purchase.getUser()));
        return entity;
    }

    public static Purchase toDomain(PurchaseEntity entity) {
        return new Purchase(
            entity.getId(),
            entity.getPurchaseDate(),
            entity.getPayMethod(),
            entity.getPrice(),
            BookMapper.toDomain(entity.getBook()),
            UserMapper.toDomain(entity.getUser())
        );
    }


}
