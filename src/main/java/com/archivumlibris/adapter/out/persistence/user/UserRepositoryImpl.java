package com.archivumlibris.adapter.out.persistence.user;

import com.archivumlibris.adapter.out.jpa.user.UserEntity;
import com.archivumlibris.adapter.out.jpa.user.UserRepositoryJPA;
import com.archivumlibris.domain.model.user.User;
import com.archivumlibris.domain.port.out.user.UserRepositoryPort;
import com.archivumlibris.mapper.user.UserMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserRepositoryPort {

    private final UserRepositoryJPA userRepositoryJpa;

    public UserRepositoryImpl(UserRepositoryJPA userRepositoryJpa) {
        this.userRepositoryJpa = userRepositoryJpa;
    }

    @Override
    public void save(User user) {
        UserEntity entity = UserMapper.toEntity(user);
        userRepositoryJpa.save(entity);
    }

    @Override
    public Page<User> findAll(
        String name,
        String email,
        Pageable pageable
    ) {
        return userRepositoryJpa
            .findAll(name, email, pageable)
            .map(UserMapper::toDomain);
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepositoryJpa.findById(id)
        .map(UserMapper::toDomain);
    }

    @Override
    public void delete(Long id) {
        userRepositoryJpa.deleteById(id);
    }

    @Override
    public void update(Long id, User user) {
        UserEntity entity = UserMapper.toEntity(user);
        entity.setId(id);
        userRepositoryJpa.save(entity);
    }
}
