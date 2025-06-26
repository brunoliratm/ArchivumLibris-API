package com.archivumlibris.adapter.out.jpa.user;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepositoryJPA extends JpaRepository<UserEntity, Long> {

    @Query(
        """
        SELECT u FROM UserEntity u
        WHERE (:name IS NULL OR LOWER(CAST(u.name AS STRING)) LIKE LOWER(CONCAT('%', CAST(:name AS string), '%')))
        AND (:email IS NULL OR LOWER(CAST(u.email AS STRING)) LIKE LOWER(CONCAT('%', CAST(:email AS string), '%')))
        """
    )
    Page<UserEntity> findAll (
        @Param("name") String name,
        @Param("email") String email,
        Pageable pageable
    );

    Optional<UserEntity> findByEmail(String email);
}
