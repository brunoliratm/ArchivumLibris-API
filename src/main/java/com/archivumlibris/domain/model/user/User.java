package com.archivumlibris.domain.model.user;


public class User {

    private Long id;
    private String name;
    private String email;
    private String password;
    private UserRole role;
    private boolean deleted = false;


    public User() {}

    public User(
        Long id,
        String name,
        String email,
        String password,
        UserRole role,
        boolean deleted
    ) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.deleted = deleted;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) { this.email = email; }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) { this.password = password; }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
