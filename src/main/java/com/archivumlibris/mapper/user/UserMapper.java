package com.archivumlibris.mapper.user;

import com.archivumlibris.adapter.out.jpa.user.UserEntity;
import com.archivumlibris.domain.model.user.User;

public class UserMapper {

    public static UserEntity toEntity(User user) {
        UserEntity entity = new UserEntity();
        entity.setId(user.getId());
        entity.setName(user.getName());
        entity.setEmail(user.getEmail());
        entity.setPassword(user.getPassword());
        entity.setRole(user.getRole());
        return entity;
    }

    public static User toDomain(UserEntity entity) {
        return new User(
            entity.getId(),
            entity.getName(),
            entity.getEmail(),
            entity.getPassword(),
            entity.getRole(),
            entity.isDeleted()
        );
    }
}
