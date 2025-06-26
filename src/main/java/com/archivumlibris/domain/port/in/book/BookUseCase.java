package com.archivumlibris.domain.port.in.book;

import java.util.List;
import java.util.Optional;
import com.archivumlibris.dto.request.book.BookRequestDTO;
import com.archivumlibris.dto.response.book.BookResponseDTO;

public interface BookUseCase {
    void create(BookRequestDTO bookRequestDTO);

    void update(Long bookId, BookRequestDTO bookRequestDTO);

    void delete(Long bookId);

    Optional<BookResponseDTO> findById(Long bookId);

    List<BookResponseDTO> findAll(
        String genre,
        String title,
        String publisher,
        String author,
        int page
    );
}
