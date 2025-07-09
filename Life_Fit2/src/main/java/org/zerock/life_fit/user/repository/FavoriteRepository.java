package org.zerock.life_fit.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.life_fit.user.domain.favorite;

import java.util.List;

public interface FavoriteRepository extends JpaRepository<favorite, Integer> {
    List<favorite> findByUserId(String userId);
    void deleteByNumAndUserId(int num, String userId); // 자기 소유만 삭제
}
