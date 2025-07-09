package org.zerock.life_fit.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.zerock.life_fit.user.dto.FavoriteDTO;
import org.zerock.life_fit.user.repository.FavoriteRepository;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;

    public List<FavoriteDTO> getFavoritesByUserId(String userId) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        return favoriteRepository.findByUserId(userId).stream()
                .map(entity -> {
                    FavoriteDTO dto = new FavoriteDTO();
                    dto.setNum(entity.getNum());
                    dto.setAddress(entity.getAddress());
                    dto.setDescription(entity.getDescription());
                    dto.setRegdate(entity.getRegdate() != null ? entity.getRegdate().format(formatter) : null);
                    return dto;
                })
                .collect(Collectors.toList());
    }

    // 즐겨찾기 삭제
    public void removeFavorite(int num, String userId) {
        favoriteRepository.deleteByNumAndUserId(num, userId);
    }
}
