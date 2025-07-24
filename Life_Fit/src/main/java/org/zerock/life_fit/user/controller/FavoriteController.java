package org.zerock.life_fit.user.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.zerock.life_fit.OAuth2User.CustomOAuth2User;
import org.zerock.life_fit.user.domain.Favorite;
import org.zerock.life_fit.user.dto.FavoriteListViewResponse;
import org.zerock.life_fit.user.dto.FavoriteViewResponse;
import org.zerock.life_fit.user.dto.UserProfileResponse;
import org.zerock.life_fit.user.service.FavoriteService;
import org.zerock.life_fit.user.service.UserService;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/member")
public class FavoriteController {
    private final FavoriteService favoriteService;
    private final UserService userService;

    @GetMapping("/favorites")
    public String getFavorites(@AuthenticationPrincipal UserDetails userDetails,
                               HttpServletRequest request, Model model) {

        if (userDetails == null) {
            return "redirect:/login";
        }

        String userId;

        // 간편 로그인 (OAuth2)
        if (userDetails instanceof CustomOAuth2User) {
            CustomOAuth2User oauthUser = (CustomOAuth2User) userDetails;
            userId = String.valueOf(oauthUser.getUser().getEmail()); // 또는 getEmail()
        }
        // 일반 로그인
        else {
            userId = userDetails.getUsername(); // 일반 로그인은 username이 userId 또는 email일 것
        }

//        List<FavoriteListViewResponse> favorites = favoriteService.findByUserId(userId)
//                .stream().map(FavoriteListViewResponse::new).toList();
//
//        model.addAttribute("favorites", favorites);
        UserProfileResponse user = userService.getProfile(userId);
        model.addAttribute("user", user);
        model.addAttribute("favorites", user.getFavoriteList());
        model.addAttribute("_csrf", request.getAttribute("_csrf"));

        return "member/favoriteList";
    }
    @GetMapping("/favorites/{num}")
    public String getFavorite(Model model, @PathVariable Integer num) {
        Favorite favorite = favoriteService.findById(num);
        model.addAttribute("favorite", new FavoriteViewResponse(favorite));
        return "member/favorites";
    }
}
