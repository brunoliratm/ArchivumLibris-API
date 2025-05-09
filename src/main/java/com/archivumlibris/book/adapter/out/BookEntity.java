package com.archivumlibris.book.adapter.out;

import com.archivumlibris.book.domain.model.BookGenre;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "books")
public class BookEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 100)
    private String title;
    @Column(nullable = false, length = 100)
    private String author;
    @Column(nullable = false, length = 100)
    private String publisher;
    @Enumerated(EnumType.STRING)
    private BookGenre genre;
    @Column(nullable = false, length = 100)
    private Double price;
}
