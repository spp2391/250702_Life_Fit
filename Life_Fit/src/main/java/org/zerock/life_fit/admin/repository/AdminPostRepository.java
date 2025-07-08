package org.zerock.life_fit.admin.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.life_fit.admin.domain.Postboard;

public interface AdminPostRepository extends JpaRepository<Postboard, Long> {

    @Query("SELECT p.bno, p.title, p.content, p.boardType, u.userId, u.nickname, p.regdate, p.moddate, p.visitcount " +
            "FROM Postboard p JOIN p.user u")
    Page<Object[]> findAllPosts(Pageable pageable);

    @Query("SELECT p.bno, p.title, p.content, p.boardType, u.userId, u.nickname, p.regdate, p.moddate, p.visitcount " +
            "FROM Postboard p JOIN p.user u WHERE u.userId = :userId")
    Page<Object[]> findPostsByUserId(@Param("userId") String userId, Pageable pageable);

    @Transactional
    @Modifying
    @Query("UPDATE Postboard p SET p.title = :title, p.content = :content WHERE p.bno = :bno")
    void updatePost(@Param("bno") Long bno, @Param("title") String title, @Param("content") String content);

    @Transactional
    @Modifying
    @Query("DELETE FROM Postboard p WHERE p.bno = :bno")
    void deleteByBno(@Param("bno") Long bno);
}
