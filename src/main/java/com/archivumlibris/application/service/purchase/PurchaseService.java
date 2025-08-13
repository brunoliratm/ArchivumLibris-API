package com.archivumlibris.application.service.purchase;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import com.archivumlibris.domain.model.book.Book;
import com.archivumlibris.domain.model.purchase.PayMethod;
import com.archivumlibris.domain.model.purchase.Purchase;
import com.archivumlibris.domain.model.user.User;
import com.archivumlibris.domain.port.in.purchase.PurchaseUseCase;
import com.archivumlibris.domain.port.out.book.BookRepositoryPort;
import com.archivumlibris.domain.port.out.purchase.PurchaseRepositoryPort;
import com.archivumlibris.domain.port.out.user.UserRepositoryPort;
import com.archivumlibris.dto.request.purchase.PurchaseRequestDTO;
import com.archivumlibris.dto.response.purchase.PurchaseResponseDTO;
import com.archivumlibris.exception.book.BookNotFoundException;
import com.archivumlibris.exception.purchase.PurchaseNotFoundException;
import com.archivumlibris.exception.user.UserNotFoundException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.archivumlibris.mapper.purchase.PurchaseDTOMapper;
import com.archivumlibris.shared.exception.InvalidDataException;
import com.archivumlibris.shared.exception.InvalidPageException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PurchaseService implements PurchaseUseCase {

    private final PurchaseRepositoryPort purchaseRepositoryPort;
    private final BookRepositoryPort bookRepositoryPort;
    private final UserRepositoryPort userRepositoryPort;

    public PurchaseService(PurchaseRepositoryPort purchaseRepositoryPort,
            BookRepositoryPort bookRepositoryPort, UserRepositoryPort userRepositoryPort) {
        this.purchaseRepositoryPort = purchaseRepositoryPort;
        this.bookRepositoryPort = bookRepositoryPort;
        this.userRepositoryPort = userRepositoryPort;
    }

    @Override
    public void create(Long userId, PurchaseRequestDTO purchaseRequestDTO) {
        User user = userRepositoryPort.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        Book book = bookRepositoryPort.findById(purchaseRequestDTO.bookId())
                .orElseThrow(BookNotFoundException::new);

        PayMethod payMethodEnum = null;
        try {
            payMethodEnum = PayMethod.valueOf(purchaseRequestDTO.payMethod());
        } catch (IllegalArgumentException e) {
            throw new InvalidDataException("Invalid payment method: " + purchaseRequestDTO.payMethod());
        }

        Purchase purchase = new Purchase(
            null,
            LocalDate.now(),
            payMethodEnum,
            book.getPrice(),
            book,
            user
        );

        purchaseRepositoryPort.create(purchase);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PurchaseResponseDTO> findAll(String payMethod, Long bookId, int page) {
        if (page < 1) {
            throw new InvalidPageException();
        }
        int pageIndex = page - 1;
        Pageable pageable = PageRequest.of(pageIndex, 10);

        PayMethod payMethodEnum = null;
        if (payMethod != null) {
            try {
                payMethodEnum = PayMethod.valueOf(payMethod.toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new InvalidDataException("Invalid payment method: " + payMethod + ". Payment method must be one of: CREDIT_CARD, DEBIT_CARD, PIX, PAYPAL, BOLETO, OTHER");
            }
        }

        Page<Purchase> purchases =
                this.purchaseRepositoryPort.findAll(payMethodEnum, bookId, pageable);
        return purchases.getContent().stream().map(PurchaseDTOMapper::toResponseDTO).toList();

    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PurchaseResponseDTO> findById(Long purchaseId) {
        Optional<Purchase> purchase = this.purchaseRepositoryPort.findById(purchaseId);
        if (purchase.isEmpty()) {
            throw new PurchaseNotFoundException();
        }

        return purchase.map(PurchaseDTOMapper::toResponseDTO);
    }

}
