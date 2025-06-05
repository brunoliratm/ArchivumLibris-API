package com.archivumlibris.book.adapter.out;

import com.archivumlibris.book.domain.model.BookGenre;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BookRepositoryJpa extends JpaRepository<BookEntity, Long> {
    @Query(
        """
        SELECT b FROM BookEntity b
        WHERE (:genre IS NULL OR b.genre =:genre)
        AND (:title IS NULL OR LOWER(CAST(b.title AS string)) LIKE LOWER(CONCAT('%', CAST(:title AS string), '%')))
        AND (:publisher IS NULL OR LOWER(CAST(b.publisher AS string)) LIKE LOWER(CONCAT('%', CAST(:publisher AS string), '%')))
        AND (:author IS NULL OR LOWER(CAST(b.author AS string)) LIKE LOWER(CONCAT('%', CAST(:author AS string), '%')))
        """
    )
    Page<BookEntity> findAll(
        @Param("genre") BookGenre genre,
        @Param("title") String title,
        @Param("publisher") String publisher,
        @Param("author") String author,
        Pageable pageable
    );
}
