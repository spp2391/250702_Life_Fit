package org.zerock.life_fit.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CommentResponseDTO {
        private Long cno;       // 댓글 고유 번호
        private String content; // 댓글 내용
        private String regdate; // 작성일자 (포맷된 문자열)
        private String writerNickname;
}
