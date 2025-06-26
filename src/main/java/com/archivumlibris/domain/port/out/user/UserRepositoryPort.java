package com.archivumlibris.domain.port.out.user;

import com.archivumlibris.domain.model.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface UserRepositoryPort {

    void save(User user);

    Page<User> findAll(
        String name,
        String email,
        Pageable pageable);

    Optional<User> findById(Long id);

    void delete(Long id);

    void update(Long id, User user);

    Optional<User> findByEmail(String email);
}
