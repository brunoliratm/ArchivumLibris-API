package com.archivumlibris.application.service.book;

import com.archivumlibris.domain.model.book.Book;
import com.archivumlibris.domain.model.book.BookGenre;
import com.archivumlibris.domain.port.in.book.BookUseCase;
import com.archivumlibris.domain.port.out.book.BookRepositoryPort;
import com.archivumlibris.dto.request.book.BookRequestDTO;
import com.archivumlibris.dto.response.book.BookResponseDTO;
import com.archivumlibris.exception.book.BookNotFoundException;
import com.archivumlibris.mapper.book.BookDTOMapper;
import com.archivumlibris.shared.exception.InvalidDataException;
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
    public void create(BookRequestDTO bookRequestDTO) {
        Book book = BookDTOMapper.toModel(bookRequestDTO);
        validateBookData(book);
        this.bookRepositoryPort.save(book);
    }

    @Override
    public void update(Long bookId, BookRequestDTO bookRequestDTO) {
        if (this.bookRepositoryPort.findById(bookId).isEmpty()) {
            throw new BookNotFoundException();
        }
        Book book = BookDTOMapper.toModel(bookRequestDTO);
        validateBookData(book);
        this.bookRepositoryPort.update(bookId, book);
    }

    @Override
    public void delete(Long bookId) {
        if (this.bookRepositoryPort.findById(bookId).isEmpty()) {
            throw new BookNotFoundException();
        }
        this.bookRepositoryPort.delete(bookId);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<BookResponseDTO> findById(Long bookId) {
        Optional<Book> book = this.bookRepositoryPort.findById(bookId);
        if (book.isEmpty()) {
            throw new BookNotFoundException();
        }
        return book.map(BookDTOMapper::toResponseDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookResponseDTO> findAll(String genre, String title, String publisher, String author,
            int page) {
        if (page < 1) {
            throw new InvalidPageException();
        }

        int pageIndex = page - 1;
        Pageable pageable = PageRequest.of(pageIndex, 10);

        BookGenre genreEnum = null;
        if (genre != null) {
            try {
                genreEnum = BookGenre.valueOf(genre.toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new InvalidDataException("Invalid genre: " + genre);
            }
        }
        Page<Book> books =
                this.bookRepositoryPort.findAll(genreEnum, title, publisher, author, pageable);
        return books.getContent().stream()
                .map(BookDTOMapper::toResponseDTO)
                .toList();
    }

    private void validateBookData(Book book) {
        if (book.getTitle() == null || book.getTitle().trim().isEmpty()) {
            throw new InvalidDataException("Book title cannot be empty");
        }
        if (book.getAuthor() == null || book.getAuthor().trim().isEmpty()) {
            throw new InvalidDataException("Book author cannot be empty");
        }
        if (book.getPrice() == null || book.getPrice().signum() == -1) {
            throw new InvalidDataException("Book price must be positive");
        }
        if (book.getPublisher() == null || book.getPublisher().trim().isEmpty()) {
            throw new InvalidDataException("Book publisher cannot be empty");
        }
        if (book.getGenre() == null) {
            throw new InvalidDataException("Book genre cannot be null");
        }
    }
}
