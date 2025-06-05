package com.archivumlibris.book.application.service;

import com.archivumlibris.book.domain.model.Book;
import com.archivumlibris.book.domain.model.BookGenre;
import com.archivumlibris.book.domain.port.in.BookUseCase;
import com.archivumlibris.book.domain.port.out.BookRepositoryPort;
import com.archivumlibris.book.exception.BookNotFoundException;
import com.archivumlibris.book.exception.InvalidBookDataException;
import com.archivumlibris.shared.exception.InvalidPageException;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BookService implements BookUseCase {

    private final BookRepositoryPort bookRepositoryPort;

    public BookService(BookRepositoryPort bookRepositoryPort) {
        this.bookRepositoryPort = bookRepositoryPort;
    }

    @Override
    public void create(Book book) {
        validateBookData(book);
        this.bookRepositoryPort.save(book);
    }

    @Override
    public void update(Long id, Book book) {
        if (!this.bookRepositoryPort.findById(id).isPresent()) {
            throw new BookNotFoundException();
        }
        validateBookData(book);
        this.bookRepositoryPort.update(id, book);
    }

    @Override
    public void delete(Long id) {
        if (!this.bookRepositoryPort.findById(id).isPresent()) {
            throw new BookNotFoundException();
        }
        this.bookRepositoryPort.delete(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Book> findById(Long id) {
        Optional<Book> book = this.bookRepositoryPort.findById(id);
        if (!book.isPresent()) {
            throw new BookNotFoundException();
        }
        return book;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> findAllBooks(
        String genre,
        String title,
        String publisher,
        String author,
        int page
    ) {
        if (page < 1) {
            throw new InvalidPageException(
                "Page number must be greater than 0"
            );
        }

        int pageIndex = page - 1;
        Pageable pageable = PageRequest.of(pageIndex, 10);

        BookGenre genreEnum = null;
        if (genre != null) {
            try {
                genreEnum = BookGenre.valueOf(genre.toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new InvalidBookDataException("Invalid genre: " + genre);
            }
        }
        Page<Book> books =
            this.bookRepositoryPort.findAll(
                    genreEnum,
                    title,
                    publisher,
                    author,
                    pageable
                );
        return books.getContent();
    }

    private void validateBookData(Book book) {
        if (book.getTitle() == null || book.getTitle().trim().isEmpty()) {
            throw new InvalidBookDataException("Book title cannot be empty");
        }
        if (book.getAuthor() == null || book.getAuthor().trim().isEmpty()) {
            throw new InvalidBookDataException("Book author cannot be empty");
        }
        if (book.getPrice() == null || book.getPrice() <= 0) {
            throw new InvalidBookDataException("Book price must be positive");
        }
        if (
            book.getPublisher() == null || book.getPublisher().trim().isEmpty()
        ) {
            throw new InvalidBookDataException(
                "Book publisher cannot be empty"
            );
        }
        if (book.getGenre() == null) {
            throw new InvalidBookDataException("Book genre cannot be null");
        }
    }
}
