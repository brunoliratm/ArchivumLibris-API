package com.archivumlibris.mapper.book;

import com.archivumlibris.domain.model.book.Book;
import com.archivumlibris.domain.model.book.BookGenre;
import com.archivumlibris.dto.request.book.BookRequestDTO;
import com.archivumlibris.dto.response.book.BookResponseDTO;

public class BookDTOMapper {

    public static Book toModel(BookRequestDTO dto) {
        Book book = new Book();
        book.setTitle(dto.title());
        book.setAuthor(dto.author());
        book.setPublisher(dto.publisher());
        book.setGenre(BookGenre.valueOf(dto.genre()));
        book.setPrice(dto.price());
        return book;
    }

    public static BookResponseDTO toResponseDTO(Book book) {
        return new BookResponseDTO(
            book.getId(),
            book.getTitle(),
            book.getAuthor(),
            book.getPublisher(),
            book.getGenre() != null ? book.getGenre().name() : null,
            book.getPrice()
        );
    }
}
