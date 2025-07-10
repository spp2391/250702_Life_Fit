package org.zerock.life_fit.user.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.zerock.life_fit.user.domain.User;
import org.zerock.life_fit.user.dto.FavoriteDTO;
import org.zerock.life_fit.user.dto.UserLoginRequest;
import org.zerock.life_fit.user.dto.UserProfileResponse;
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

    // 회원가입, 로그인, 로그아웃 기능
    /*@PostMapping("/join")
    public UserProfileResponse register(@Valid @RequestBody UserRegisterRequest dto, HttpSession session) {
        User newUser = userService.register(dto);
        session.setAttribute("UserId", newUser.getUserId());
        return UserProfileResponse.fromEntity(newUser);
    }*/

    @GetMapping("/join")
    public String joinPage() {
        return "member/join"; // → templates/join.html 같은 뷰 이름
    }

    /*@PostMapping("/login")
    public UserProfileResponse login(@Valid @RequestBody UserLoginRequest dto, HttpSession session) {
        User user = userService.login(dto);
        session.setAttribute("userId", user.getUserId());
        return UserProfileResponse.fromEntity(user);
    }*/

    @GetMapping("/login")
    public String login() {

        return "member/login";
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "/";
    }

    @GetMapping("/profile")
    public String profilePage(Principal principal, Model model) {
        String userId = principal.getName();

        model.addAttribute("user", userService.getProfile(userId));
        List<FavoriteDTO> favoriteList = favoriteService.getFavoritesByUserId(String.valueOf(userId));
        model.addAttribute("favoriteList", favoriteList);

        return "member/profile";
    }

    /*@DeleteMapping("/favorite/{num}")
    @ResponseBody
    public ResponseEntity<?> removeFavorite(@PathVariable int num, HttpSession session) {
        String userId = (String) session.getAttribute("userId");
        favoriteService.removeFavorite(num, userId);
        return ResponseEntity.ok().build();
    }*/

    // 회원정보 수정
    /*@PostMapping("/update")
    public UserProfileResponse updateProfile(@Valid @RequestBody UserRegisterRequest dto, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        return userService.updateUser(userId, dto);
    }*/

    @PostMapping("/update")
    public String updateProfile(@Valid UserRegisterRequest dto, Principal principal) {
        userService.updateUser(principal.getName(), dto);
        return "redirect:/member/profile";
    }

    // 회원 탈퇴
    @PostMapping("/withdraw")
    public String deleteUser(HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        userService.deleteUser(userId);
        session.invalidate();
        return "회원 탈퇴가 완료되었습니다.";
    }

    /*// 즐겨찾기 목록 조회 (예시)
    @GetMapping("/favorites")
    public List<String> getFavorites(HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        return userService.getFavorites(userId);
    }*/

    // 관리자 여부 확인
    @GetMapping("/admin")
    public String checkAdmin(HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        boolean isAdmin = userService.isAdmin(userId);
        return isAdmin ? "관리자 페이지로 이동합니다." : "권한이 없습니다.";
    }


}
