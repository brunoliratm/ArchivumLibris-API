package com.archivumlibris.adapter.out.persistence.user;

import com.archivumlibris.adapter.out.jpa.user.UserEntity;
import com.archivumlibris.domain.model.user.User;
import com.archivumlibris.domain.port.out.user.UserRepositoryPort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Optional;

public class UserRepositoryImpl implements UserRepositoryPort {
    @Override
    public void save(User user) {
        UserEntity userEntity = new UserEntity();
    }

    @Override
    public Page<User> findAll(String name, String email, Pageable pageable) {
        return null;
    }

    @Override
    public Optional<User> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public void update(Long id, User user) {

    }
}
