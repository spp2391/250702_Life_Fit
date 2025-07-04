package org.zerock.life_fit.admin.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.zerock.life_fit.admin.dto.UserDTO;
import org.zerock.life_fit.admin.service.AdminService;

import java.util.List;

@Controller
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    // 🔹 전체 목록 출력
    @GetMapping("/test")
    public String adminTestPage(Model model) {
        model.addAttribute("users", adminService.getAllUsers());
        return "admintest";
    }

    // 🔍 회원 검색
    @GetMapping("/search")
    public String searchUsers(@RequestParam(required = false) String userId,
                              @RequestParam(required = false) String username,
                              @RequestParam(required = false) String role,
                              Model model) {
        List<UserDTO> filteredUsers = adminService.searchUsers(userId, username, role);
        model.addAttribute("users", filteredUsers);
        return "admintest";
    }

    // ✏️ 회원 정보 수정
    @PostMapping("/users/{userId}")
    public String updateUser(@PathVariable String userId, @ModelAttribute UserDTO dto) {
        adminService.updateUser(userId, dto);
        return "redirect:/api/admin/test";
    }

    // 🗑 회원 삭제
    @PostMapping("/users/{userId}/delete")
    public String deleteUser(@PathVariable String userId) {
        adminService.deleteUser(userId);
        return "redirect:/api/admin/test";
    }

    // 🔁 권한 변경
    @PostMapping("/users/{userId}/role")
    public String changeRole(@PathVariable String userId, @RequestParam String role) {
        adminService.changeUserRole(userId, role);
        return "redirect:/api/admin/test";
    }

    // 🔐 비밀번호 초기화
    @PostMapping("/users/{userId}/reset-password")
    public String resetPassword(@PathVariable String userId) {
        adminService.resetPassword(userId);
        return "redirect:/api/admin/test";
    }
}
