package com.archivumlibris.book.dto.request;

public record BookRequestDTO(
        String title,
        String author,
        String publisher,
        String genre,
        Double price
) {
}
