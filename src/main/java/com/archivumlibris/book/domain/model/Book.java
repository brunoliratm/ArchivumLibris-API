package com.archivumlibris.book.domain.model;

public class Book {
    private Long id;
    private String title;
    private String author;
    private String publisher;
    private BookGenre genre;
    private Double price;

    public Book() {
    }

    public Book(Long id, String title, String author, String publisher, BookGenre genre, Double price) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.genre = genre;
        this.price = price;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public BookGenre getGenre() {
        return genre;
    }

    public void setGenre(BookGenre genre) {
        this.genre = genre;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
