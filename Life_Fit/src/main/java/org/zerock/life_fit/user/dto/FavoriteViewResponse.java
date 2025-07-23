package org.zerock.life_fit.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.zerock.life_fit.user.domain.Favorite;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class FavoriteViewResponse {
    private Long userId;
    private Long id;              // 즐겨찾기 ID
    private String address;       // 프로그램 주소
    private String description;   // 설명
    private LocalDateTime regdate;
    private String url;
    public FavoriteViewResponse(Favorite favorite) {
        this.id = favorite.getId();
        this.address = favorite.getAddress();
        this.description = favorite.getDescription();
        this.userId = favorite.getUser().getUserId();
        this.regdate = favorite.getRegdate();
        this.url = favorite.getUrl();
    }
}
