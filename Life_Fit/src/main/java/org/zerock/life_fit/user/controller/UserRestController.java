package org.zerock.life_fit.user.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.life_fit.user.domain.User;
import org.zerock.life_fit.user.dto.UserProfileResponse;
import org.zerock.life_fit.user.dto.UserRegisterRequest;
import org.zerock.life_fit.user.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class UserRestController {
    private final UserService userService;

    // 회원가입, 로그인, 로그아웃 기능
    @PostMapping("/join")
    public User register(@Valid @RequestBody UserRegisterRequest dto, HttpSession session) {
        User newUser = userService.register(dto);
        session.setAttribute("userId", newUser.getUserId());
        return newUser;
    }
}
