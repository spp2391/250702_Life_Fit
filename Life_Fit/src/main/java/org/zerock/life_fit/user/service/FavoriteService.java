package org.zerock.life_fit.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.zerock.life_fit.user.domain.favorite;
import org.zerock.life_fit.user.dto.FavoriteDTO;
import org.zerock.life_fit.user.repository.FavoriteRepository;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FavoriteService {
    private final FavoriteRepository favoriteRepository;

    public List<favorite> findall() {
        return favoriteRepository.findAll();
    }

    public favorite findById(int id) {
        return favoriteRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Not Found" + id));
    }

    public String delete(int id, String userId) {
        favorite favorite = favoriteRepository.findById(id).get();
        if(favorite.getUserId().equals(userId)) {
            favoriteRepository.deleteById(id);
            return "삭제되었습니다.";
        } else {
            return "실패하였습니다.";
        }
    }



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
    public void removeFavorite(int id, String userId) {
        favorite favorite = favoriteRepository.findById(id).get();
        if(favorite.getUserId().equals(userId)) {
            favoriteRepository.deleteById(id);
        }
    }
}
