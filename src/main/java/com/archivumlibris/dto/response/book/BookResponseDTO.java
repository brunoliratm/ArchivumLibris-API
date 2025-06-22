package com.archivumlibris.dto.response.book;

public record BookResponseDTO(
    Long id,
    String title,
    String author,
    String publisher,
    String genre,
    Double price
) {}
