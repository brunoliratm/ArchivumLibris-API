package com.archivumlibris.adapter.out.jpa.user;

import com.archivumlibris.domain.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepositoryJPA extends JpaRepository<User, Long> {

}
