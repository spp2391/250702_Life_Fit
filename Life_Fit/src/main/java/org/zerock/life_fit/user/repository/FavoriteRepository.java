package org.zerock.life_fit.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.life_fit.user.domain.Favorite;
import org.zerock.life_fit.user.domain.User;

import java.util.List;
import java.util.Optional;

public interface FavoriteRepository extends JpaRepository<Favorite, Integer> {
    List<Favorite> findByUser(User user);
//    void deleteByNumAndUserId(int num, String userId); // 자기 소유만 삭제
    boolean existsByUserAndUrl(User user, String url);
    void deleteByUserAndUrl(User user, String url);
}
