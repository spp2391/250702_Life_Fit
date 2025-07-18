package org.zerock.life_fit.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.life_fit.user.domain.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByName(String userId);
}
