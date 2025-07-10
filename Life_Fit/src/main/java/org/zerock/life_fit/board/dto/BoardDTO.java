package org.zerock.life_fit.board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.zerock.life_fit.board.domain.Board;


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
    private LocalDateTime regdate;
    private LocalDateTime moddate;
    private int visitcount;
    private int likes;
    private String userId;
    private String localCateName;
    private String keyword;

    public BoardDTO(Board board) {
        this.bno = board.getBno();
        this.boardType = board.getBoardType();
        if(board.getLocalCate() != null){
            this.localCateId = board.getLocalCate().getLocalnum();
            this.localCateName = board.getLocalCate().getLocalname();  // 추가
        } else {
            this.localCateId = null;
            this.localCateName = null;
        }
        this.title = board.getTitle();
        this.content = board.getContent();
       /* this.userId = board.getUser() != null ? board.getUser().getUserId() : null;*/
        this.regdate = board.getRegdate();
        this.moddate = board.getModdate();
        this.visitcount = board.getVisitcount();
        this.likes = board.getLikes();
        /*this.userId = board.getUser() != null ? board.getUser().getUserId() : null;*/
    }
}
