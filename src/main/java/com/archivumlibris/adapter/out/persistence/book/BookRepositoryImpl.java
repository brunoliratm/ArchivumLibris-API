package com.archivumlibris.adapter.out.persistence.book;

import com.archivumlibris.adapter.out.jpa.book.BookEntity;
import com.archivumlibris.adapter.out.jpa.book.BookRepositoryJPA;
import com.archivumlibris.domain.model.book.Book;
import com.archivumlibris.domain.model.book.BookGenre;
import com.archivumlibris.domain.port.out.book.BookRepositoryPort;
import com.archivumlibris.mapper.book.BookMapper;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public class BookRepositoryImpl implements BookRepositoryPort {

    private final BookRepositoryJPA bookRepositoryJpa;

    public BookRepositoryImpl(BookRepositoryJPA bookRepositoryJpa) {
        this.bookRepositoryJpa = bookRepositoryJpa;
    }

    @Override
    public void save(Book book) {
        BookEntity entity = BookMapper.toEntity(book);
        bookRepositoryJpa.save(entity);
    }

    @Override
    public Page<Book> findAll(
        BookGenre genre,
        String title,
        String publisher,
        String author,
        Pageable pageable
    ) {
        return bookRepositoryJpa
            .findAll(genre, title, publisher, author, pageable)
            .map(BookMapper::toDomain);
    }

    @Override
    public Optional<Book> findById(Long id) {
        return bookRepositoryJpa.findById(id)
        .map(BookMapper::toDomain);
    }

    @Override
    public void delete(Long id) {
        bookRepositoryJpa.deleteById(id);
    }

    @Override
    public void update(Long id, Book book) {
        BookEntity entity = BookMapper.toEntity(book);
        entity.setId(id);
        bookRepositoryJpa.save(entity);
    }
}
