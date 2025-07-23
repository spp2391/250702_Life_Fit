package org.zerock.life_fit.user.dto;

import lombok.Getter;
import org.zerock.life_fit.user.domain.Favorite;

import java.time.LocalDateTime;

@Getter
public class FavoriteResponse {
    private final String userId;
    private final String address;
    private final String description;
    private final LocalDateTime regdate;
    public FavoriteResponse(Favorite favorite) {
        this.userId = favorite.getUserId();
        this.address = favorite.getAddress();
        this.description = favorite.getDescription();
        this.regdate = favorite.getRegdate();
    }
}
