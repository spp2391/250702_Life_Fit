package org.zerock.life_fit.admin.dto;

import lombok.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentDTO {
    private Long cno;
    private String content;
    private LocalDateTime regdate;
    private Long boardId;
}

