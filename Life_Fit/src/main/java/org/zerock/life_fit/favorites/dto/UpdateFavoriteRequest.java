package org.zerock.life_fit.favorites.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UpdateFavoriteRequest {
    private String address;
    private String description;
}
