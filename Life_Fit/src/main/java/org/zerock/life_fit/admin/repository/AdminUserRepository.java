package org.zerock.life_fit.admin.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.zerock.life_fit.user.domain.User;

import java.util.List;
import java.util.Optional;

public interface AdminUserRepository extends JpaRepository<User, Long> {

    // 로그인용
    Optional<User> findByEmail(String email);

    // 이메일 + 닉네임 + 역할 검색 (native 쿼리)
    @Query(value = "SELECT * FROM `user` " +
            "WHERE (:email IS NULL OR :email = '' OR email LIKE %:email%) " +
            "AND (:nickname IS NULL OR :nickname = '' OR nickname LIKE %:nickname%) " +
            "AND (:user_role IS NULL OR :user_role = '' OR user_role = :user_role)",
            nativeQuery = true)
    List<User> searchUsers(
            @Param("email") String email,
            @Param("nickname") String nickname,
            @Param("user_role") String user_role
    );

    // 이름 + 역할 검색 (페이징 포함, native 쿼리)
    @Query(value = "SELECT * FROM `user` " +
            "WHERE (:name IS NULL OR :name = '' OR name LIKE %:name%) " +
            "AND (:user_role IS NULL OR :user_role = '' OR user_role = :user_role)",
            countQuery = "SELECT COUNT(*) FROM `user` " +
                    "WHERE (:name IS NULL OR :name = '' OR name LIKE %:name%) " +
                    "AND (:user_role IS NULL OR :user_role = '' OR user_role = :user_role)",
            nativeQuery = true)
    Page<User> searchUsersWithPaging(
            @Param("name") String name,
            @Param("user_role") String user_role,
            Pageable pageable
    );
}
