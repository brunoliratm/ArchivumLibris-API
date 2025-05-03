package com.archivumlibris.book.domain.port;

import com.archivumlibris.book.domain.model.Book;
import com.archivumlibris.book.domain.model.BookGenre;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface BookRepositoryPort {
    void save(Book book);

    Page<Book> findAll(BookGenre genre, String title, String publisher, String author, Pageable pageable);

    Optional<Book> findById(Long id);

    void delete(Long id);

    void update(Long id, Book book);
}
