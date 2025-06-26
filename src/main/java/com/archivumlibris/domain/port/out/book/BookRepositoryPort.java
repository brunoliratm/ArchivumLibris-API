package com.archivumlibris.domain.port.out.book;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.archivumlibris.domain.model.book.Book;
import com.archivumlibris.domain.model.book.BookGenre;

public interface BookRepositoryPort {
    void save(Book book);

    Page<Book> findAll(
            BookGenre genre,
            String title,
            String publisher,
            String author,
            Pageable pageable);

    Optional<Book> findById(Long id);

    void delete(Long id);

    void update(Long id, Book book);
}
