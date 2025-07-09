package org.zerock.life_fit.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.zerock.life_fit.user.domain.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, String> {

    @Query(value = "SELECT * FROM user " +
            "WHERE (:userId IS NULL OR :userId = '' OR user_id LIKE %:userId%) " +
            "AND (:username IS NULL OR :username = '' OR username LIKE %:username%) " +
            "AND (:role IS NULL OR :role = '' OR role = :role)",
            nativeQuery = true)
    List<User> searchUsers(
            @Param("userId") String userId,
            @Param("username") String username,
            @Param("role") String role
    );
}
