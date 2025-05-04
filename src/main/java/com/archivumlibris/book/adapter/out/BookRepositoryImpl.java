package com.archivumlibris.book.adapter.out;

import com.archivumlibris.book.domain.model.Book;
import com.archivumlibris.book.domain.model.BookGenre;
import com.archivumlibris.book.domain.port.out.BookRepositoryPort;
import com.archivumlibris.book.mapper.BookMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class BookRepositoryImpl implements BookRepositoryPort {

    private final BookRepositoryJpa bookRepositoryJpa;

    public BookRepositoryImpl(BookRepositoryJpa bookRepositoryJpa) {
        this.bookRepositoryJpa = bookRepositoryJpa;
    }

    @Override
    public void save(Book book) {
        BookEntity entity = BookMapper.toEntity(book);
        bookRepositoryJpa.save(entity);
    }

    @Override
    public Page<Book> findAll(BookGenre genre, String title, String publisher, String author, Pageable pageable) {
        return bookRepositoryJpa.findAll(genre, title, publisher, author, pageable).map(BookMapper::toDomain);
    }

    @Override
    public Optional<Book> findById(Long id) {
        return bookRepositoryJpa.findById(id).map(BookMapper::toDomain);
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
