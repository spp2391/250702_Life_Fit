package org.zerock.life_fit.board.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.zerock.life_fit.board.domain.Board;

public interface PageRepository extends JpaRepository<Board,Long> {
    // 예: 자유/주제 게시판 타입별 페이징 조회
    Page<Board> findByBoardType(String boardType, Pageable pageable);

    // 예: 지역 + 게시판 타입으로 페이징 조회
    @Query("SELECT b FROM Board b WHERE b.boardType = :boardType AND b.localCate.localnum = :localId")
    Page<Board> findByBoardTypeAndLocalCate(@Param("boardType") String boardType,
                                            @Param("localId") Long localId,
                                            Pageable pageable);
    // 3. boardType + keyword (제목 or 내용에 포함)
    @Query("SELECT b FROM Board b " +
            "WHERE b.boardType = :boardType " +
            "AND (LOWER(b.title) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(b.content) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    Page<Board> findByBoardTypeAndKeyword(
            @Param("boardType") String boardType,
            @Param("keyword") String keyword,
            Pageable pageable);

    // 4. boardType + 지역 + keyword
    @Query("SELECT b FROM Board b " +
            "WHERE b.boardType = :boardType " +
            "AND b.localCate.localnum = :localId " +
            "AND (LOWER(b.title) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(b.content) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    Page<Board> findByBoardTypeAndLocalCateAndKeyword(
            @Param("boardType") String boardType,
            @Param("localId") Long localId,
            @Param("keyword") String keyword,
            Pageable pageable);

    @Query("SELECT b FROM Board b WHERE b.boardType = 'FREE' AND " +
                  "(b.title LIKE %:keyword% OR b.content LIKE %:keyword%)")
    Page<Board> searchFreeBoard(@Param("keyword") String keyword, Pageable pageable);

    // 제목만 검색
    @Query("SELECT b FROM Board b WHERE b.boardType = :boardType AND LOWER(b.title) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<Board> findByBoardTypeAndTitleContaining(@Param("boardType") String boardType,
                                                  @Param("keyword") String keyword,
                                                  Pageable pageable);

    // 내용만 검색
    @Query("SELECT b FROM Board b WHERE b.boardType = :boardType AND LOWER(b.content) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<Board> findByBoardTypeAndContentContaining(@Param("boardType") String boardType,
                                                    @Param("keyword") String keyword,
                                                    Pageable pageable);

    // 제목+내용 검색 (기존꺼 재활용 가능)
    @Query("SELECT b FROM Board b WHERE b.boardType = :boardType AND " +
            "(LOWER(b.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(b.content) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    Page<Board> findByBoardTypeAndTitleOrContentContaining(@Param("boardType") String boardType,
                                                           @Param("keyword") String keyword,
                                                           Pageable pageable);

    @Query("SELECT b FROM Board b WHERE b.boardType = :boardType AND b.localCate.localnum = :localId AND LOWER(b.content) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<Board> findByBoardTypeAndLocalCateAndContentContaining(
            @Param("boardType") String boardType,
            @Param("localId") Long localId,
            @Param("keyword") String keyword,
            Pageable pageable);
    @Query("SELECT b FROM Board b WHERE b.boardType = :boardType AND b.localCate.localnum = :localId AND LOWER(b.title) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<Board> findByBoardTypeAndLocalCateAndTitleContaining(
            @Param("boardType") String boardType,
            @Param("localId") Long localId,
            @Param("keyword") String keyword,
            Pageable pageable);
}
