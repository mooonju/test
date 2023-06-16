package com.test1.repository;

import com.test1.domain.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserId(String userId);

    /* Id 중복 확인 */
    boolean existsByUserId(String userId);
}
