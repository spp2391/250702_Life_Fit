package org.zerock.life_fit.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.zerock.life_fit.user.domain.Favorite;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheckFavoriteDTO {
    private Long id;              // 즐겨찾기 ID
    private String address;       // 프로그램 주소
    private String description;   // 설명
    private String title; // 장소명
    private String url; // API URL
    private Long user_id; // USERID
    private LocalDateTime regdate;        // 등록일 (String 또는 LocalDateTime)
    public static CheckFavoriteDTO toDTO (Favorite favorite) {
        return CheckFavoriteDTO.builder()
                .id(favorite.getId())
                .user_id(favorite.getUser().getUserId())
                .url(favorite.getUrl())
                .description(favorite.getDescription())
                .title(favorite.getTitle())
                .regdate(favorite.getRegdate())
                .address(favorite.getAddress())
                .build();
    }
}