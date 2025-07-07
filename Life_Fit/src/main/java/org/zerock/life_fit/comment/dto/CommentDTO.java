package org.zerock.life_fit.comment.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CommentDTO {
    private Long boardId;
    private String content;
}
