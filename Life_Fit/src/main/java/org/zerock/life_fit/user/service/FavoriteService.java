package org.zerock.life_fit.user.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.zerock.life_fit.user.domain.Favorite;
import org.zerock.life_fit.user.domain.User;
import org.zerock.life_fit.user.dto.CheckFavoriteDTO;
import org.zerock.life_fit.user.dto.FavoriteAddRequest;
import org.zerock.life_fit.user.dto.FavoriteDTO;
import org.zerock.life_fit.user.repository.FavoriteRepository;

import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FavoriteService {
    private final FavoriteRepository favoriteRepository;

    public void add(FavoriteAddRequest favoriteAddRequest, User user) throws Exception {
        favoriteRepository.save(favoriteAddRequest.toEntity(user));
    }

    public List<Favorite> findall() {
        return favoriteRepository.findAll();
    }

    public Favorite findById(int id) {
        return favoriteRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Not Found" + id));
    }

    public String delete(int id, Long userId) {
        Favorite favorite = favoriteRepository.findById(id).get();
        if(favorite.getUser().getUserId().equals(userId)) {
            favoriteRepository.deleteById(id);
            return "삭제되었습니다.";
        } else {
            return "실패하였습니다.";
        }
    }

    public boolean findByUserIdAndUrl(User user, String url) {

        return favoriteRepository.existsByUserAndUrl(user, url);

    }

    public List<FavoriteDTO> getFavoritesByUserId(User user) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        return favoriteRepository.findByUser(user).stream()
                .map(entity -> {
                    FavoriteDTO dto = new FavoriteDTO();
                    dto.setId(entity.getId());
                    dto.setAddress(entity.getAddress());
                    dto.setDescription(entity.getDescription());
                    dto.setRegdate(entity.getRegdate() != null ? entity.getRegdate().format(formatter) : null);
                    return dto;
                })
                .collect(Collectors.toList());
    }

    // 즐겨찾기 삭제
    public void removeFavorite(int id, Long userId) {
        Favorite favorite = favoriteRepository.findById(id).get();
        if(favorite.getUser().getUserId().equals(userId)) {
            favoriteRepository.deleteById(id);
        }
    }

    @Transactional
    public void removeFavoriteByUrl( User user, String url){
        favoriteRepository.deleteByUserAndUrl(user, url);
    }

//    public List<Favorite> findByUserId(String userId) {
//        return favoriteRepository.findByUserId(userId);
//    }
}
