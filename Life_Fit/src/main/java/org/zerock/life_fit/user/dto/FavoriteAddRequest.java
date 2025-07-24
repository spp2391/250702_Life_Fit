package org.zerock.life_fit.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.zerock.life_fit.user.domain.Favorite;
import org.zerock.life_fit.user.domain.User;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FavoriteAddRequest {
    private String address;       // 프로그램 주소
    private String description;   // 설명
    private String title; // 장소명
    private String url; // API URL
    private Long userId; // USERID
    private double lat;
    private double lng;

    public Favorite toEntity(User user) {
        return Favorite.builder()
                .title(this.title)
                .address(this.address)
                .description(this.description)
                .url(this.url)
                .user(user)
                .lat(this.lat)
                .lng(this.lng)
                .build();
    }
}
