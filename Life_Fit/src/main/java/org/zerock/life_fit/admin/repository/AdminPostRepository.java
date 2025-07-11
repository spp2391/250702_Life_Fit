package org.zerock.life_fit.admin.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.life_fit.board.domain.Board;

public interface AdminPostRepository extends JpaRepository<Board, Long> {

    @Query("SELECT p.bno, p.title, p.content, p.boardType, u.userId, u.nickname, p.regdate, p.moddate, p.visitcount " +
            "FROM Board p JOIN p.user u")
    Page<Object[]> findAllPosts(Pageable pageable);

    @Query("SELECT p.bno, p.title, p.content, p.boardType, u.userId, u.nickname, p.regdate, p.moddate, p.visitcount " +
            "FROM Board p JOIN p.user u WHERE u.userId = :userId")
    Page<Object[]> findPostsByUserId(@Param("userId") Long userId, Pageable pageable);

    @Transactional
    @Modifying
    @Query("UPDATE Board p SET p.title = :title, p.content = :content WHERE p.bno = :bno")
    void updatePost(@Param("bno") Long bno, @Param("title") String title, @Param("content") String content);

    @Transactional
    @Modifying
    @Query("DELETE FROM Board p WHERE p.bno = :bno")
    void deleteByBno(@Param("bno") Long bno);
}
