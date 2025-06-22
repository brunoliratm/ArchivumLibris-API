package com.archivumlibris.domain.port.in.book;

import java.util.List;
import java.util.Optional;
import com.archivumlibris.domain.model.book.Book;

public interface BookUseCase {
    void create(Book book);

    void update(Long id, Book book);

    void delete(Long id);

    Optional<Book> findById(Long id);

    List<Book> findAllBooks(
        String genre,
        String title,
        String publisher,
        String author,
        int page
    );
}
