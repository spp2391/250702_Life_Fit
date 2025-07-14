package org.zerock.life_fit.admin.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminPageController {

    // 관리자 메인 패널 화면
    @GetMapping("/panel")
    public String adminPanel() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("🔐 로그인 사용자 이메일: " + auth.getName());
        System.out.println("🔐 로그인 사용자 권한 목록: " + auth.getAuthorities());

        // templates/admin/adminpanel.html 파일을 렌더링
        return "admin/adminpanel";
    }

    // 테스트용 페이지
    @GetMapping("/test")
    public String testPage() {
        // templates/admin/admintest.html 파일을 렌더링
        return "admin/admintest";
    }
}
