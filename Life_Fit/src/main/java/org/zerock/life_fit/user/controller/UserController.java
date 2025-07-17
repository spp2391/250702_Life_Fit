package org.zerock.life_fit.user.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.zerock.life_fit.user.dto.FavoriteDTO;
import org.zerock.life_fit.user.dto.UserRegisterRequest;
import org.zerock.life_fit.user.service.FavoriteService;
import org.zerock.life_fit.user.service.UserService;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class UserController {

    private final UserService userService;
    private final FavoriteService favoriteService;

    @GetMapping("/join")
    public String joinPage() {
        return "member/join";
    }

    @GetMapping("/login")
    public String login() {
        return "member/login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/member/login";
    }

    @GetMapping("/profile")
    public String profilePage(Principal principal, Model model) {
        if (principal == null) {
            return "redirect:/member/login";
        }

        String userId = principal.getName();
        model.addAttribute("user", userService.getProfile(userId));
        List<FavoriteDTO> favoriteList = favoriteService.getFavoritesByUserId(userId);
        model.addAttribute("favoriteList", favoriteList);

        return "member/profile";
    }

    @PostMapping("/update")
    public String updateProfile(@Valid UserRegisterRequest dto, Principal principal) {
        if (principal == null) {
            return "redirect:/member/login";
        }

        userService.updateUser(principal.getName(), dto);
        return "redirect:/member/profile";
    }

    @PostMapping("/withdraw")
    public String deleteUser(HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        userService.deleteUser(userId);
        session.invalidate();
        return "redirect:/";
    }

    @GetMapping("/admin")
    public String checkAdmin(HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        boolean isAdmin = userService.isAdmin(userId);
        return isAdmin ? "관리자 페이지로 이동합니다." : "권한이 없습니다.";
    }
}
