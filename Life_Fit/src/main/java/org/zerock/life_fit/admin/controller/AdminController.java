package org.zerock.life_fit.admin.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.zerock.life_fit.admin.dto.UserDTO;
import org.zerock.life_fit.admin.service.AdminService;

@Controller
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    // 관리자 메인 패널 (사이드바 포함)
    @GetMapping("/panel")
    public String adminPanel() {
        return "admin/adminpanel";
    }

    // 회원관리 iframe 로딩용
    @GetMapping("/test")
    public String adminTestPage(@RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "10") int size,
                                Model model) {

        Page<UserDTO> userPage = adminService.getUsersWithPaging(page, size);
        model.addAttribute("userPage", userPage);
        return "admin/admintest"; // 회원관리 템플릿
    }

    @GetMapping("/search")
    public String searchUsers(@RequestParam(required = false) String name,
                              @RequestParam(required = false) String role,
                              @RequestParam(defaultValue = "0") int page,
                              @RequestParam(defaultValue = "10") int size,
                              Model model) {
        Page<UserDTO> filteredUsers = adminService.searchUsersWithPaging(null, name, role, page, size);
        model.addAttribute("userPage", filteredUsers);
        return "admin/admintest";
    }

    //회원 정보 수정
    @PostMapping("/users/{userId}")
    public String updateUser(@PathVariable String userId, @ModelAttribute UserDTO dto) {
        adminService.updateUser(userId, dto);
        return "redirect:/api/admin/test";
    }

    // 회원 삭제
    @PostMapping("/users/{userId}/delete")
    public String deleteUser(@PathVariable String userId) {
        adminService.deleteUser(userId);
        return "redirect:/api/admin/test";
    }

    // 권한 변경
    @PostMapping("/users/{userId}/role")
    public String changeRole(@PathVariable String userId, @RequestParam String role) {
        adminService.changeUserRole(userId, role);
        return "redirect:/api/admin/test";
    }

    //  비밀번호 초기화
    @PostMapping("/users/{userId}/reset-password")
    public String resetPassword(@PathVariable String userId) {
        adminService.resetPassword(userId);
        return "redirect:/api/admin/test";
    }
}
