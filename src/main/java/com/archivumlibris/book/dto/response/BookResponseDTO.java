package com.archivumlibris.book.dto.response;

public record BookResponseDTO(
        Long id,
        String title,
        String author,
        String publisher,
        String genre,
        Double price
) {
}
