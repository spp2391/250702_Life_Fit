package org.zerock.life_fit.favorites.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.zerock.life_fit.favorites.domain.Favorites;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class SaveFavoriteRequest {
    private String userId;
    private String name;
    private String address;
    private String description="";
    private String url;
    public Favorites toEntity() {
        return Favorites.builder()
                .userId(userId)
                .name(name)
                .address(address)
                .description(description)
                .url(url)
                .build();
    }
}
