package org.zerock.life_fit.admin.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostDTO {
    private Long bno;
    private String title;
    private String content;
    private String boardType;
    private String userId;
    private String nickname;
    private LocalDateTime regdate;
    private LocalDateTime moddate;
    private int visitcount;
}
