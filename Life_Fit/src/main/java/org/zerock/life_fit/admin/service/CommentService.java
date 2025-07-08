package org.zerock.life_fit.admin.service;

import org.zerock.life_fit.admin.dto.CommentDTO;
import java.util.List;

public interface CommentService {
    List<CommentDTO> getCommentsByPost(Long bno);
    void deleteComment(Long cno);
    void updateComment(Long cno,String content);
}
