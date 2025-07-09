package org.zerock.life_fit.favorites.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.zerock.life_fit.favorites.domain.Favorites;

import java.util.List;

public interface FavoritesRepository extends JpaRepository<Favorites, Long> {
    @Query("SELECT f FROM Favorites f WHERE f.userId=:userId ORDER BY f.num ASC")
    List<Favorites> findByUserId(String userId);

    @Query("SELECT f FROM Favorites f WHERE f.userId=:userId AND f.address=:address")
    Favorites findByUserIdAndAddress(String userId, String address);

    @Query("SELECT COUNT(f) FROM Favorites f WHERE f.userId=:userId AND f.address=:address")
    long countFavorites(String userId, String address);

    boolean existsFavoritesByUserIdAndAddress(String userId,  String address);
}
