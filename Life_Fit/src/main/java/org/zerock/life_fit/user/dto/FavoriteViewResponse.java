package org.zerock.life_fit.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.zerock.life_fit.user.domain.favorite;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class FavoriteViewResponse {
    private String userId;
    private int num;              // 즐겨찾기 ID
    private String address;       // 프로그램 주소
    private String description;   // 설명
    private LocalDateTime regdate;
    public FavoriteViewResponse(favorite favorite) {
        this.num = favorite.getNum();
        this.address = favorite.getAddress();
        this.description = favorite.getDescription();
        this.userId = favorite.getUserId();
        this.regdate = favorite.getRegdate();
    }
}
