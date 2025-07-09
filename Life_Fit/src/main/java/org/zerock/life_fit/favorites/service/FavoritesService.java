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

    // 즐겨찾기 설정
    // 중요: 같은 장소에 즐겨찾기를 중복 설정하지 않도록 코드를 짜야 한다!!
    public Favorites saveFavorite(SaveFavoriteRequest saveFavoriteRequest) {return favoritesRepository.save(saveFavoriteRequest.toEntity());}

    // 특정 유저의 즐겨찾기 열람
    public List<Favorites> readFavorites(String userId) {return favoritesRepository.findByUserId(userId);}

    // 즐겨찾기 수정 (num, 수정 내용)
    @Transactional
    public Favorites updateFavorite(long num, UpdateFavoriteRequest updateFavoriteRequest) {
        Favorites favorite = favoritesRepository.findById(num)
                .orElseThrow(()->new RuntimeException("Favorite not found"+num));
        favorite.update(updateFavoriteRequest.getDescription());
        return favorite;
    }

    // 유저명이랑 주소를 기반으로 즐겨찾기 번호를 찾는다.
    public long findNum(String userId, String address) {
        return favoritesRepository.findByUserIdAndAddress(userId,address).getNum();
    }

    public boolean checkFavorites(String userId, String address) {
        return favoritesRepository.existsFavoritesByUserIdAndAddress(userId,address);
    }

    // 즐겨찾기 번호를 기반으로 즐겨찾기를 해제한다.
    public void unsaveFavorite(Long num) {favoritesRepository.deleteById(num);}


}
