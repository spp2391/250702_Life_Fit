
package org.zerock.life_fit.comment.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.zerock.life_fit.board.domain.Board;
import org.zerock.life_fit.board.repository.BoardRepository;
import org.zerock.life_fit.comment.Repository.CommentRepository;
import org.zerock.life_fit.comment.domain.Comment;
import org.zerock.life_fit.comment.dto.CommentDTO;
import org.zerock.life_fit.comment.dto.CommentResponseDTO;
import org.zerock.life_fit.user.domain.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;

    public void addComment(CommentDTO dto, User user) {
        Board board = boardRepository.findById(dto.getBoardId())
                .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));

        Comment comment = Comment.builder()
                .content(dto.getContent())
                .board(board)
                .writer(user)
                .build();
        comment.setRegdate(LocalDateTime.now());
        commentRepository.save(comment);
    }





    public List<CommentResponseDTO> getCommentsByBoard(Long boardId) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        return commentRepository.findByBoard_BnoOrderByRegdateAsc(boardId)
                .stream()
                .map(comment -> CommentResponseDTO.builder()
                        .cno(comment.getCno())
                        .content(comment.getContent())
                        .regdate(comment.getRegdate() != null ? comment.getRegdate().format(formatter) : "")
                        .writerNickname(comment.getWriter() != null ? comment.getWriter().getNickname() : "익명")
                        .build())
                .collect(Collectors.toList());
    }




    public void deleteComment(Long cno) {
        commentRepository.deleteById(cno);
    }

    public void updateComment(Long cno, String content) {
        Comment comment = commentRepository.findById(cno)
                .orElseThrow(() -> new IllegalArgumentException("댓글 없음"));

        comment.setContent(content);
        comment.setRegdate(LocalDateTime.now()); // 수정 시간도 갱신하고 싶다면
        commentRepository.save(comment);
    }

    public boolean isCommentOwner(Long cno, User user) {
        Comment comment = commentRepository.findById(cno)
                .orElseThrow(() -> new IllegalArgumentException("댓글이 존재하지 않습니다."));

        if (comment.getWriter() == null || user == null) {
            return false;
        }

        return comment.getWriter().getUserId().equals(user.getUserId());
    }


}

