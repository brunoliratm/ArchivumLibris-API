package com.archivumlibris.domain.model.user;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import com.archivumlibris.domain.model.book.Book;

public class User {

    private UUID id;
    private String name;
    private String email;
    private String password;
    private UserRole role;
    private List<Book> favoriteBooks;

    public User(
        UUID id,
        String name,
        String email,
        String password,
        UserRole role,
        List<Book> favoriteBooks
    ) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.favoriteBooks = favoriteBooks != null
            ? favoriteBooks
            : Collections.emptyList();
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public UserRole getRole() {
        return role;
    }

    public List<Book> getFavoriteBooks() {
        return Collections.unmodifiableList(favoriteBooks);
    }

    public boolean isAdmin() {
        return role == UserRole.ADMIN;
    }

    public boolean isUser() {
        return role == UserRole.USER;
    }

    public void addFavoriteBook(Book book) {
        if (!favoriteBooks.contains(book)) {
            favoriteBooks.add(book);
        }
    }

    public void removeFavoriteBook(Book book) {
        favoriteBooks.remove(book);
    }

    public void updateProfile(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public void updatePassword(String encodedPassword) {
        this.password = encodedPassword;
    }

    public void promoteToAdmin() {
        this.role = UserRole.ADMIN;
    }

    public void demoteToUser() {
        this.role = UserRole.USER;
    }
}
