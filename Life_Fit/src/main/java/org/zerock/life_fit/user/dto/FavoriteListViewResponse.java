package org.zerock.life_fit.user.dto;

import lombok.Getter;
import org.zerock.life_fit.user.domain.Favorite;

import java.time.LocalDateTime;

@Getter
public class FavoriteListViewResponse {
    private final Long id;
    private final Long userId;
    private final String discription;
    private final String address;
    private final LocalDateTime regdate;
    private final LocalDateTime moddate;
    private final String url;
    public  FavoriteListViewResponse(Favorite favorite) {
        this.userId = favorite.getUser().getUserId();
        this.discription = favorite.getDescription();
        this.regdate = favorite.getRegdate();
        this.id = favorite.getId();
        this.address = favorite.getAddress();
        this.url = favorite.getUrl();
        this.moddate = favorite.getModdate();
    }
}
