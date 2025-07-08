package org.zerock.life_fit.admin.repository;

import org.zerock.life_fit.admin.domain.PostComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<PostComment, Long> {
    List<PostComment> findByBno(Long bno);
}