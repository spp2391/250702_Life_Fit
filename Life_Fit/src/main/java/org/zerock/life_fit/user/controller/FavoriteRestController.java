package org.zerock.life_fit.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.life_fit.user.dto.FavoriteDTO;
import org.zerock.life_fit.user.dto.FavoriteResponse;
import org.zerock.life_fit.user.service.FavoriteService;

import java.security.Principal;

@RequiredArgsConstructor
@RestController
public class FavoriteRestController {
    private final FavoriteService favoriteService;

    @GetMapping("/mainscreen/favorite")
    public void addFavorite(FavoriteDTO favoriteDTO) {

    }

    @DeleteMapping("/member/favorites/{id}")
    public ResponseEntity<String>  deleteFavorite(
            @PathVariable("id") Integer id, Principal principal) {
        if(principal.getName() == null) {
            return ResponseEntity.internalServerError().build();
        }
        String result = favoriteService.delete(id, principal.getName());
        if (result.equals("삭제되었습니다.")){
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().body(result);
        }

    }
}
