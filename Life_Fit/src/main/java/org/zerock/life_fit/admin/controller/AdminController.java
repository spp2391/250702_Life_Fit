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

    @GetMapping("/test")
    public String adminTestPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model) {

        Page<UserDTO> userPage = adminService.getUsersWithPaging(page, size);
        model.addAttribute("userPage", userPage);
        return "admin/admintest";
    }


    @GetMapping("/search")
    public String searchUsers(@RequestParam(required = false) String email,
                              @RequestParam(required = false) String username,
                              @RequestParam(required = false) String role,
                              @RequestParam(defaultValue = "0") int page,
                              @RequestParam(defaultValue = "10") int size,
                              Model model) {

        Page<UserDTO> filteredUsers = adminService.searchUsersWithPaging(email, username, role, page, size);
        model.addAttribute("userPage", filteredUsers);
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
