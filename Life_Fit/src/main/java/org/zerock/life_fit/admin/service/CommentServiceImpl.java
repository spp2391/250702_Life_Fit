package org.zerock.life_fit.admin.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.zerock.life_fit.admin.domain.PostComment;
import org.zerock.life_fit.admin.dto.CommentDTO;
import org.zerock.life_fit.admin.repository.CommentRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    @Override
    public List<CommentDTO> getCommentsByPost(Long bno) {
        return commentRepository.findByBno(bno).stream()
                .map(c -> new CommentDTO(
                        c.getCno(),
                        c.getBno(),
                        c.getWriter(),
                        c.getComment(),
                        c.getRegdate()))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteComment(Long cno) {
        commentRepository.deleteById(cno);
    }
    public void updateComment(Long cno, String newComment) {
        PostComment comment = commentRepository.findById(cno)
                .orElseThrow(() -> new RuntimeException("댓글 없음"));
        comment.setComment(newComment);
        comment.setModdate(LocalDateTime.now());
        commentRepository.save(comment);
    }

}
