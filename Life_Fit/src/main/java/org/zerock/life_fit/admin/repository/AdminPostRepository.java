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

    // 전체 게시글 조회 (닉네임 포함)
    @Query("SELECT p.bno, p.title, p.content, p.boardType, u.userId, u.nickname, u.name, p.regdate, p.moddate, p.visitcount " +
            "FROM Board p JOIN p.writer u")
    Page<Object[]> findAllPosts(Pageable pageable);

    // ✅ 닉네임 기준 게시글 검색 추가
    @Query("SELECT p.bno, p.title, p.content, p.boardType, u.userId, u.nickname, u.name, p.regdate, p.moddate, p.visitcount " +
            "FROM Board p JOIN p.writer u WHERE u.nickname LIKE %:nickname%")
    Page<Object[]> findPostsByNickname(@Param("nickname") String nickname, Pageable pageable);

    // 기존 검색 메서드들 (원하면 삭제 가능)
    @Query("SELECT p.bno, p.title, p.content, p.boardType, u.userId, u.nickname, u.name, p.regdate, p.moddate, p.visitcount " +
            "FROM Board p JOIN p.writer u WHERE u.userId = :userId")
    Page<Object[]> findPostsByUserId(@Param("userId") Long userId, Pageable pageable);

    @Query("SELECT p.bno, p.title, p.content, p.boardType, u.userId, u.nickname, u.name, p.regdate, p.moddate, p.visitcount " +
            "FROM Board p JOIN p.writer u WHERE u.name LIKE %:name%")
    Page<Object[]> findPostsByUserName(@Param("name") String nameText, Pageable pageable);

    // 게시글 수정
    @Transactional
    @Modifying
    @Query("UPDATE Board p SET p.title = :title, p.content = :content WHERE p.bno = :bno")
    void updatePost(@Param("bno") Long bno, @Param("title") String title, @Param("content") String content);

    // 게시글 삭제
    @Transactional
    @Modifying
    @Query("DELETE FROM Board p WHERE p.bno = :bno")
    void deleteByBno(@Param("bno") Long bno);
}
