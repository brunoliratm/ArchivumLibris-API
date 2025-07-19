package com.archivumlibris.adapter.out.jpa.purchase;

import java.math.BigDecimal;
import java.time.LocalDate;
import com.archivumlibris.adapter.out.jpa.book.BookEntity;
import com.archivumlibris.adapter.out.jpa.user.UserEntity;
import com.archivumlibris.domain.model.purchase.PayMethod;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "purchases")
public class PurchaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate purchaseDate;

    @Enumerated(EnumType.STRING)
    private PayMethod payMethod;

    private BigDecimal price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private BookEntity book;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
