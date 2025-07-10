package org.zerock.life_fit.comment.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.life_fit.comment.domain.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByBoard_BnoOrderByRegdateAsc(Long boardId);
}
