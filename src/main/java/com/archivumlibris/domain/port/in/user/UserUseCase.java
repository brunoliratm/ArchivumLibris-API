package com.archivumlibris.domain.port.in.user;

import java.util.List;
import java.util.Optional;
import com.archivumlibris.domain.model.user.User;

public interface UserUseCase {

    void createUser(User user);

    void updateUser(Long userId, User user);

    void deleteUser(Long userId);

    Optional<User> findById(Long userId);

    List<User> findAllUsers(String name, String email, int page);

}
