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
    private int bno;
    private String boardType;
    private Long localCateId; // nullable
    private String title;
    private String content;
    private String userId;
    private LocalDateTime regdate;
    private LocalDateTime moddate;
    private int visitcount;
    private int likes;

}
