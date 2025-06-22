package com.archivumlibris.mapper.book;

import com.archivumlibris.adapter.out.jpa.book.BookEntity;
import com.archivumlibris.domain.model.book.Book;

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
