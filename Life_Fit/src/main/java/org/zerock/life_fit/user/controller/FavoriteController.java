package org.zerock.life_fit.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.zerock.life_fit.user.domain.favorite;
import org.zerock.life_fit.user.dto.FavoriteListViewResponse;
import org.zerock.life_fit.user.dto.FavoriteViewResponse;
import org.zerock.life_fit.user.service.FavoriteService;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/member")
public class FavoriteController {
    private final FavoriteService favoriteService;
    @GetMapping("/favorites")
    public String getFavorites(Model model) {
        List<FavoriteListViewResponse> favorites = favoriteService.findall()
                .stream().map(FavoriteListViewResponse::new).toList();
        model.addAttribute("favorites", favorites);
        return "member/favoriteList";
    }
    @GetMapping("/favorites/{num}")
    public String getFavorite(Model model, @PathVariable Integer num) {
        favorite favorite = favoriteService.findById(num);
        model.addAttribute("favorite", new FavoriteViewResponse(favorite));
        return "member/favorites";
    }
}
