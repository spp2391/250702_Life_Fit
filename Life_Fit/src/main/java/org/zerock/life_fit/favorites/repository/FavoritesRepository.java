package org.zerock.life_fit.favorites.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.zerock.life_fit.favorites.domain.Favorites;

import java.util.List;

public interface FavoritesRepository extends JpaRepository<Favorites, Long> {
    @Query(nativeQuery = true, value = "SELECT * FROM Favorites WHERE userId=:userId ORDER BY num ASC")
    List<Favorites> findByUserId(String userId);
}
