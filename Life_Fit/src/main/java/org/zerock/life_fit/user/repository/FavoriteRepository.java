package org.zerock.life_fit.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.life_fit.user.domain.Favorite;

import java.util.List;
import java.util.Optional;

public interface FavoriteRepository extends JpaRepository<Favorite, Integer> {
    List<Favorite> findByUserId(String userId);
//    void deleteByNumAndUserId(int num, String userId); // 자기 소유만 삭제
    boolean existsByUserIdAndUrl(String userId, String url);
    void deleteByUserIdAndUrl(String userId, String url);
}
