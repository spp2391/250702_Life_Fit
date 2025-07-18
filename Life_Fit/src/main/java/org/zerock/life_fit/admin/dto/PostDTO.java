package org.zerock.life_fit.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostDTO {
    private Long bno;
    private String title;
    private String content;
    private String boardType;
    private Long userId;
    private String nickname;
    private LocalDateTime regdate;
    private LocalDateTime moddate;
    private int visitcount;
    private String email;
}
