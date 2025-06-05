package com.archivumlibris.book.mapper;

import com.archivumlibris.book.adapter.out.BookEntity;
import com.archivumlibris.book.domain.model.Book;

public class BookMapper {

    public static BookEntity toEntity(Book book) {
        BookEntity entity = new BookEntity();
        entity.setId(book.getId());
        entity.setTitle(book.getTitle());
        entity.setAuthor(book.getAuthor());
        entity.setPublisher(book.getPublisher());
        entity.setGenre(book.getGenre());
        entity.setPrice(book.getPrice());
        return entity;
    }

    public static Book toDomain(BookEntity entity) {
        return new Book(
            entity.getId(),
            entity.getTitle(),
            entity.getAuthor(),
            entity.getPublisher(),
            entity.getGenre(),
            entity.getPrice()
        );
    }
}
