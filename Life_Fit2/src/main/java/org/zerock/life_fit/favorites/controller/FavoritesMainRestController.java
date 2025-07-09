package org.zerock.life_fit.favorites.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.life_fit.favorites.dto.SaveFavoriteRequest;
import org.zerock.life_fit.favorites.service.FavoritesService;

@RestController
@RequiredArgsConstructor
public class FavoritesMainRestController {
    private final FavoritesService favoritesService;

    @PostMapping("/favorite/add")
    public void addFavorite(@RequestBody SaveFavoriteRequest saveFavoriteRequest) {
        favoritesService.saveFavorite(saveFavoriteRequest);
    }

    @PostMapping("/favorite/remove")
    public void removeFavorite(@RequestBody SaveFavoriteRequest saveFavoriteRequest) {
        Long num = favoritesService.findNum(saveFavoriteRequest.getUserId(), saveFavoriteRequest.getAddress());
        favoritesService.unsaveFavorite(num);
    }


}
