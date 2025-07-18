package org.zerock.life_fit.admin.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.zerock.life_fit.admin.dto.CommentDTO;
import org.zerock.life_fit.admin.repository.AdminCommentRepository;
import org.zerock.life_fit.comment.domain.Comment;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminCommentService {

    private final AdminCommentRepository adminCommentRepository;

    public List<CommentDTO> getCommentsByPost(Long bno) {
        return adminCommentRepository.findByBno(bno).stream()
                .map(c -> new CommentDTO(
                        c.getCno(),
                        c.getContent(),
                        c.getRegdate(),
                        c.getBoard().getBno()
                ))
                .collect(Collectors.toList());
    }

    public void deleteComment(Long cno) {
        adminCommentRepository.deleteById(cno);
    }

    public void updateComment(Long cno, String newContent) {
        Comment comment = adminCommentRepository.findById(cno)
                .orElseThrow(() -> new RuntimeException("❌ 댓글이 존재하지 않습니다"));
        comment.setContent(newContent);
        adminCommentRepository.save(comment);
    }
}
