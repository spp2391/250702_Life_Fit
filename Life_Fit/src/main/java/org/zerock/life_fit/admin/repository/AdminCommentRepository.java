package org.zerock.life_fit.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.life_fit.comment.domain.Comment;

import java.util.List;

public interface AdminCommentRepository extends JpaRepository<Comment, Long> {

    @Query("SELECT c FROM Comment c WHERE c.board.bno = :bno")
    List<Comment> findByBno(@Param("bno") Long bno);

    @Transactional
    @Modifying
    @Query("DELETE FROM Comment c WHERE c.board.bno = :bno")
    void deleteByBoardId(@Param("bno") Long bno);
}
