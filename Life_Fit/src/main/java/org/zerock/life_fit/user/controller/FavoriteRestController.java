package org.zerock.life_fit.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.zerock.life_fit.user.dto.CheckFavoriteDTO;
import org.zerock.life_fit.user.dto.FavoriteAddRequest;
import org.zerock.life_fit.user.dto.FavoriteDTO;
import org.zerock.life_fit.user.dto.FavoriteResponse;
import org.zerock.life_fit.user.service.FavoriteService;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class FavoriteRestController {
    private final FavoriteService favoriteService;
    @PostMapping("/api/mainscreen/favorite")
    public void addFavorite(@RequestBody FavoriteAddRequest favoriteAddRequest, Principal principal) {
        try {
            favoriteAddRequest.setUserId(principal.getName());
            favoriteService.add(favoriteAddRequest);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    @DeleteMapping("/api/mainscreen/favorite/delete")
    public void deleteFavorite(@RequestBody FavoriteAddRequest favoriteAddRequest, Principal principal) {
        try {
            favoriteService.removeFavoriteByUrl(principal.getName(), favoriteAddRequest.getUrl());
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
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

    @GetMapping("/api/mainscreen/favorites/{url}")
    public ResponseEntity<Map<String, String>> checkFavorite(
            @PathVariable("url") String url, Principal principal) {
        if(principal.getName() == null) {
            System.out.println("로그인되지 않은 상태.");
            Map<String, String> response = new HashMap<>();
            response.put("data", "Not logged in");
            return ResponseEntity.ok(response);
        }
        url = "http://place.map.kakao.com/"+url;
        boolean result = favoriteService.findByUserIdAndUrl(principal.getName(), url);
        if (result) {
            Map<String, String> response = new HashMap<>();
            response.put("data", "Favorited");
            return ResponseEntity.ok(response);
        } else {
            Map<String, String> response = new HashMap<>();
            response.put("data", "Not Favorited");
            return ResponseEntity.ok(response);
        }

    }
}
