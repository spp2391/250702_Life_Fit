package org.zerock.life_fit.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.zerock.life_fit.user.domain.Favorite;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FavoriteAddRequest {
    private String address;       // 프로그램 주소
    private String description;   // 설명
    private String title; // 장소명
    private String url; // API URL
    private String userId; // USERID

    public Favorite toEntity() {
        return Favorite.builder()
                .title(this.title)
                .address(this.address)
                .description(this.description)
                .url(this.url)
                .userId(this.userId)
                .build();
    }
}
