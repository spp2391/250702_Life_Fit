package org.zerock.life_fit.user.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.zerock.life_fit.user.domain.Favorite;
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
    public String getFavorites(HttpServletRequest request, Model model) {
        List<FavoriteListViewResponse> favorites = favoriteService.findall()
                .stream().map(FavoriteListViewResponse::new).toList();
        model.addAttribute("favorites", favorites);
        CsrfToken csrfToken = (CsrfToken) request.getAttribute("_csrf");
        model.addAttribute("_csrf", csrfToken);
        return "member/favoriteList";
    }
    @GetMapping("/favorites/{num}")
    public String getFavorite(Model model, @PathVariable Integer num) {
        Favorite favorite = favoriteService.findById(num);
        model.addAttribute("favorite", new FavoriteViewResponse(favorite));
        return "member/favorites";
    }
}
