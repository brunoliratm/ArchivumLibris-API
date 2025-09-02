package com.archivumlibris.services;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import java.math.BigDecimal;
import java.time.LocalDate;
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
import com.archivumlibris.application.service.purchase.PurchaseService;
import com.archivumlibris.domain.model.book.Book;
import com.archivumlibris.domain.model.purchase.PayMethod;
import com.archivumlibris.domain.model.purchase.Purchase;
import com.archivumlibris.domain.model.user.User;
import com.archivumlibris.domain.port.out.book.BookRepositoryPort;
import com.archivumlibris.domain.port.out.purchase.PurchaseRepositoryPort;
import com.archivumlibris.domain.port.out.user.UserRepositoryPort;
import com.archivumlibris.dto.request.purchase.PurchaseRequestDTO;
import com.archivumlibris.dto.response.purchase.PurchaseResponseDTO;
import com.archivumlibris.exception.book.BookNotFoundException;
import com.archivumlibris.exception.purchase.PurchaseNotFoundException;
import com.archivumlibris.exception.user.UserNotFoundException;
import com.archivumlibris.shared.exception.InvalidDataException;
import com.archivumlibris.shared.exception.InvalidPageException;

class PurchaseServiceTest {

    @Mock
    private PurchaseRepositoryPort purchaseRepositoryPort;
    @Mock
    private BookRepositoryPort bookRepositoryPort;
    @Mock
    private UserRepositoryPort userRepositoryPort;

    @InjectMocks
    private PurchaseService purchaseService;

    private User user;
    private Book book;
    private Purchase purchase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User(1L, "User", "user@email.com", "pass",
                com.archivumlibris.domain.model.user.UserRole.USER, false);
        book = new Book(1L, "Title", "Author", "Publisher",
                com.archivumlibris.domain.model.book.BookGenre.FICTION, BigDecimal.valueOf(10));
        purchase = new Purchase(1L, LocalDate.now(), PayMethod.PIX, BigDecimal.valueOf(10), book,
                user);
    }

    @Test
    void testCreatePurchaseSuccess() {
        PurchaseRequestDTO dto = new PurchaseRequestDTO(1L, "PIX");
        when(userRepositoryPort.findById(1L)).thenReturn(Optional.of(user));
        when(bookRepositoryPort.findById(1L)).thenReturn(Optional.of(book));
        assertDoesNotThrow(() -> purchaseService.create(1L, dto));
    }

    @Test
    void testCreatePurchaseUserNotFound() {
        PurchaseRequestDTO dto = new PurchaseRequestDTO(1L, "PIX");
        when(userRepositoryPort.findById(1L)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> purchaseService.create(1L, dto));
    }

    @Test
    void testCreatePurchaseBookNotFound() {
        PurchaseRequestDTO dto = new PurchaseRequestDTO(1L, "PIX");
        when(userRepositoryPort.findById(1L)).thenReturn(Optional.of(user));
        when(bookRepositoryPort.findById(1L)).thenReturn(Optional.empty());
        assertThrows(BookNotFoundException.class, () -> purchaseService.create(1L, dto));
    }

    @Test
    void testCreatePurchaseInvalidPayMethod() {
        PurchaseRequestDTO dto = new PurchaseRequestDTO(1L, "INVALID");
        when(userRepositoryPort.findById(1L)).thenReturn(Optional.of(user));
        when(bookRepositoryPort.findById(1L)).thenReturn(Optional.of(book));
        assertThrows(InvalidDataException.class, () -> purchaseService.create(1L, dto));
    }

    @Test
    void testFindAllSuccess() {
        Page<Purchase> page = new PageImpl<>(List.of(purchase));
        when(purchaseRepositoryPort.findAll(any(), any(), any(Pageable.class))).thenReturn(page);
        List<PurchaseResponseDTO> result = purchaseService.findAll("PIX", 1L, 1);
        assertEquals(1, result.size());
    }

    @Test
    void testFindAllInvalidPage() {
        assertThrows(InvalidPageException.class, () -> purchaseService.findAll("PIX", 1L, 0));
    }

    @Test
    void testFindAllInvalidPayMethod() {
        assertThrows(InvalidDataException.class, () -> purchaseService.findAll("INVALID", 1L, 1));
    }

    @Test
    void testFindByIdSuccess() {
        when(purchaseRepositoryPort.findById(1L)).thenReturn(Optional.of(purchase));
        Optional<PurchaseResponseDTO> result = purchaseService.findById(1L);
        assertTrue(result.isPresent());
    }

    @Test
    void testFindByIdNotFound() {
        when(purchaseRepositoryPort.findById(1L)).thenReturn(Optional.empty());
        assertThrows(PurchaseNotFoundException.class, () -> purchaseService.findById(1L));
    }
}
