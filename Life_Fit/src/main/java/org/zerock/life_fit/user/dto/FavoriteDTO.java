package org.zerock.life_fit.user.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FavoriteDTO {
    private int num;              // 즐겨찾기 ID
    private String address;       // 프로그램 주소
    private String description;   // 설명
    private String regdate;        // 등록일 (String 또는 LocalDateTime)
}