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
    private Long localCateId;         // 지역 카테고리 ID (nullable)
    private String title;
    private String content;
    private LocalDateTime regdate;
    private LocalDateTime moddate;
    private int visitcount;
    private int likes;

    private String userId;            // 작성자 ID (String 타입으로 출력용)
    private String userNickname;      // 작성자 닉네임
    private String localCateName;     // 지역 카테고리명 (nullable)

    private String keyword;           // 검색용 필드

    // Entity -> DTO 변환 생성자
    public BoardDTO(Board board) {
        this.bno = board.getBno();
        this.boardType = board.getBoardType();

        if (board.getLocalCate() != null) {
            this.localCateId = board.getLocalCate().getLocalnum();
            this.localCateName = board.getLocalCate().getLocalname();
        } else {
            this.localCateId = null;
            this.localCateName = null;
        }

        this.title = board.getTitle();
        this.content = board.getContent();
        this.regdate = board.getRegdate();
        this.moddate = board.getModdate();
        this.visitcount = board.getVisitcount();
        this.likes = board.getLikes();

        this.userId = board.getWriter() != null ? board.getWriter().getUserId().toString() : null;
        this.userNickname = board.getWriter() != null ? board.getWriter().getNickname() : null;
    }
}
