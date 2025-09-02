package com.archivumlibris.services;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import com.archivumlibris.application.service.book.BookService;
import com.archivumlibris.domain.model.book.Book;
import com.archivumlibris.domain.model.book.BookGenre;
import com.archivumlibris.domain.port.out.book.BookRepositoryPort;
import com.archivumlibris.dto.request.book.BookRequestDTO;
import com.archivumlibris.dto.response.book.BookResponseDTO;
import com.archivumlibris.exception.book.BookNotFoundException;
import com.archivumlibris.shared.exception.InvalidDataException;

class BookServiceTest {

    @Mock
    private BookRepositoryPort bookRepositoryPort;

    @InjectMocks
    private BookService bookService;

    private Book book;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        book = new Book(1L, "Title", "Author", "Publisher", BookGenre.FICTION,
                BigDecimal.valueOf(10));
    }

    @Test
    void testCreateBookSuccess() {
        BookRequestDTO dto = new BookRequestDTO("Title", "Author", "Publisher", "FICTION",
                BigDecimal.valueOf(10));
        doNothing().when(bookRepositoryPort).save(any(Book.class));
        assertDoesNotThrow(() -> bookService.create(dto));
    }

    @Test
    void testCreateBookInvalidData() {
        BookRequestDTO dto =
                new BookRequestDTO("", "Author", "Publisher", "FICTION", BigDecimal.valueOf(10));
        assertThrows(InvalidDataException.class, () -> bookService.create(dto));
    }

    @Test
    void testUpdateBookSuccess() {
        BookRequestDTO dto = new BookRequestDTO("Title", "Author", "Publisher", "FICTION",
                BigDecimal.valueOf(10));
        when(bookRepositoryPort.findById(1L)).thenReturn(Optional.of(book));
        assertDoesNotThrow(() -> bookService.update(1L, dto));
    }

    @Test
    void testUpdateBookNotFound() {
        BookRequestDTO dto = new BookRequestDTO("Title", "Author", "Publisher", "FICTION",
                BigDecimal.valueOf(10));
        when(bookRepositoryPort.findById(1L)).thenReturn(Optional.empty());
        assertThrows(BookNotFoundException.class, () -> bookService.update(1L, dto));
    }

    @Test
    void testDeleteBookSuccess() {
        when(bookRepositoryPort.findById(1L)).thenReturn(Optional.of(book));
        assertDoesNotThrow(() -> bookService.delete(1L));
    }

    @Test
    void testDeleteBookNotFound() {
        when(bookRepositoryPort.findById(1L)).thenReturn(Optional.empty());
        assertThrows(BookNotFoundException.class, () -> bookService.delete(1L));
    }

    @Test
    void testFindByIdSuccess() {
        when(bookRepositoryPort.findById(1L)).thenReturn(Optional.of(book));
        Optional<BookResponseDTO> result = bookService.findById(1L);
        assertTrue(result.isPresent());
        assertEquals("Title", result.get().title());
    }

    @Test
    void testFindByIdNotFound() {
        when(bookRepositoryPort.findById(1L)).thenReturn(Optional.empty());
        assertThrows(BookNotFoundException.class, () -> bookService.findById(1L));
    }

    @Test
    void testFindAllSuccess() {
        Page<Book> page = new PageImpl<>(List.of(book));
        when(bookRepositoryPort.findAll(any(), any(), any(), any(), any(Pageable.class)))
                .thenReturn(page);
        List<BookResponseDTO> result =
                bookService.findAll("FICTION", "Title", "Publisher", "Author", 1);
        assertEquals(1, result.size());
    }

    @Test
    void testFindAllInvalidPage() {
        assertThrows(com.archivumlibris.shared.exception.InvalidPageException.class,
                () -> bookService.findAll("FICTION", "Title", "Publisher", "Author", 0));
    }

    @Test
    void testFindAllInvalidGenre() {
        assertThrows(InvalidDataException.class,
                () -> bookService.findAll("INVALID_GENRE", "Title", "Publisher", "Author", 1));
    }
}
