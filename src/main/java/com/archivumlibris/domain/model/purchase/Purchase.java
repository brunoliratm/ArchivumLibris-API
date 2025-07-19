package com.archivumlibris.domain.model.purchase;

import java.math.BigDecimal;
import java.time.LocalDate;
import com.archivumlibris.domain.model.book.Book;
import com.archivumlibris.domain.model.user.User;

public class Purchase {

    private Long id;
    private LocalDate purchaseDate;
    private PayMethod payMethod;
    private BigDecimal price;
    private Book book;
    private User user;

    public Purchase() {}

    public Purchase(
        Long id,
        LocalDate purchaseDate,
        PayMethod payMethod,
        BigDecimal price,
        Book book,
        User user
    ) {
        this.id = id;
        this.purchaseDate = purchaseDate;
        this.payMethod = payMethod;
        this.price = price;
        this.book = book;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public PayMethod getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(PayMethod payMethod) {
        this.payMethod = payMethod;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
