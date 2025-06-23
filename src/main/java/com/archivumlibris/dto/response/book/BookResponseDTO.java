package com.archivumlibris.dto.response.book;

import java.math.BigDecimal;

public record BookResponseDTO(
    Long id,
    String title,
    String author,
    String publisher,
    String genre,
    BigDecimal price
) {}
