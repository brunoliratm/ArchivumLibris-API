package com.archivumlibris.book.domain.port.in;

import com.archivumlibris.book.domain.model.Book;
import java.util.List;
import java.util.Optional;

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
