package com.archivumlibris.book.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public record BookRequestDTO(
    @NotBlank(message = "Title is required") String title,
    @NotBlank(message = "Author is required") String author,
    @NotBlank(message = "Publisher is required") String publisher,
    @NotBlank(message = "Genre is required")
    @Pattern(
        regexp = "^(FICTION|FANTASY|SCIENCE_FICTION|MYSTERY|THRILLER|HORROR|ROMANCE|HISTORICAL_FICTION|LITERARY_FICTION|NON_FICTION|BIOGRAPHY_MEMOIR|HISTORY|SCIENCE|PHILOSOPHY|PSYCHOLOGY|SELF_HELP|BUSINESS_FINANCE|POLITICS|RELIGION_SPIRITUALITY|CLASSICS|POETRY|DRAMA|GRAPHIC_NOVEL|COOKBOOK|TRAVEL|ACADEMIC)$",
        message = "Genre must be a valid BookGenre enum value"
    )
    String genre,
    @NotNull(message = "Price is required")
    @Positive(message = "Price must be positive")
    Double price
) {}
