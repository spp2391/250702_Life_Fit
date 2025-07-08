package org.zerock.life_fit.admin.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.zerock.life_fit.user.domain.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, String> {

    @Query(value = "SELECT * FROM user " +
            "WHERE (:email IS NULL OR :email = '' OR email LIKE %:email%) " +
            "AND (:username IS NULL OR :username = '' OR username LIKE %:username%) " +
            "AND (:role IS NULL OR :role = '' OR role = :role)",
            countQuery = "SELECT COUNT(*) FROM user " +
                    "WHERE (:email IS NULL OR :email = '' OR email LIKE %:email%) " +
                    "AND (:username IS NULL OR :username = '' OR username LIKE %:username%) " +
                    "AND (:role IS NULL OR :role = '' OR role = :role)",
            nativeQuery = true)
    Page<User> searchUsersWithPaging(
            @Param("email") String email,
            @Param("username") String username,
            @Param("role") String role,
            Pageable pageable
    );

    @Query(value = "SELECT * FROM user " +
            "WHERE (:email IS NULL OR :email = '' OR email LIKE %:email%) " +
            "AND (:username IS NULL OR :username = '' OR username LIKE %:username%) " +
            "AND (:role IS NULL OR :role = '' OR role = :role)",
            nativeQuery = true)
    List<User> searchUsers(
            @Param("email") String email,
            @Param("username") String username,
            @Param("role") String role
    );

}

