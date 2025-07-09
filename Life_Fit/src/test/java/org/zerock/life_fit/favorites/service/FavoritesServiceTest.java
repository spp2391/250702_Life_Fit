package org.zerock.life_fit.favorites.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.life_fit.favorites.dto.SaveFavoriteRequest;
import org.zerock.life_fit.favorites.repository.FavoritesRepository;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest

class FavoritesServiceTest {
    @Autowired
    private FavoritesService favoritesService;
    @Autowired
    private FavoritesRepository favoritesRepository;
    @Test
    public void addTest() {
    }
    @Test
    public void addTest2() {
        System.out.println(favoritesRepository.existsFavoritesByUserIdAndAddress("user_id123", "addres"));
    }
}