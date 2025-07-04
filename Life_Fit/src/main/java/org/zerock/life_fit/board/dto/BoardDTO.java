package org.zerock.life_fit.board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.zerock.life_fit.board.domain.Board;
import org.zerock.life_fit.user.domain.User;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BoardDTO {
    private Long bno;
    private String boardType;
    private Long localCateId; // nullable
    private String title;
    private String content;
    private String userId;
    private LocalDateTime regdate;
    private LocalDateTime moddate;
    private int visitcount;
    private int likes;

    public BoardDTO(Board board) {
        this.bno = board.getBno();
        this.boardType = board.getBoardType();
        this.localCateId = board.getLocalCate() != null ? board.getLocalCate().getLocalnum() : null;
        this.title = board.getTitle();
        this.content = board.getContent();
       /* this.userId = board.getUser() != null ? board.getUser().getUserId() : null;*/
        this.regdate = board.getRegdate();
        this.moddate = board.getModdate();
        this.visitcount = board.getVisitcount();
        this.likes = board.getLikes();
    }
}
