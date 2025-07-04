package org.zerock.life_fit.favorites.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.zerock.life_fit.favorites.domain.Favorites;
import org.zerock.life_fit.favorites.dto.SaveFavoriteRequest;
import org.zerock.life_fit.favorites.dto.UpdateFavoriteRequest;
import org.zerock.life_fit.favorites.repository.FavoritesRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class FavoritesService {
    private final FavoritesRepository favoritesRepository;

    public Favorites saveFavorite(SaveFavoriteRequest saveFavoriteRequest) {return favoritesRepository.save(saveFavoriteRequest.toEntity());}

    public List<Favorites> readFavorites(String userId) {return favoritesRepository.findByUserId(userId);}

    @Transactional
    public Favorites updateFavorite(long num, UpdateFavoriteRequest updateFavoriteRequest) {
        Favorites favorite = favoritesRepository.findById(num)
                .orElseThrow(()->new RuntimeException("Favorite not found"+num));
        favorite.update(updateFavoriteRequest.getAddress(), updateFavoriteRequest.getDescription());
        return favorite;
    }
    public void unsaveFavorite(Long num) {favoritesRepository.deleteById(num);}


}
