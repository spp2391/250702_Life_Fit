package org.zerock.life_fit.user.dto;

import lombok.Getter;
import org.zerock.life_fit.user.domain.favorite;

import java.time.LocalDateTime;

@Getter
public class FavoriteListViewResponse {
    private final Integer num;
    private final String userId;
    private final String discription;
    private final String address;
    private final LocalDateTime regdate;
    public  FavoriteListViewResponse(favorite favorite) {
        this.userId = favorite.getUserId();
        this.discription = favorite.getDescription();
        this.regdate = favorite.getRegdate();
        this.num = favorite.getNum();
        this.address = favorite.getAddress();
    }
}
