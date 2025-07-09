package org.zerock.life_fit.user.dto;

import lombok.Getter;
import org.zerock.life_fit.user.domain.favorite;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
public class FavoriteResponse {
    private final String userId;
    private final String address;
    private final String description;
    private final LocalDateTime regdate;
    public FavoriteResponse(favorite favorite) {
        this.userId = favorite.getUserId();
        this.address = favorite.getAddress();
        this.description = favorite.getDescription();
        this.regdate = favorite.getRegdate();
    }
}
