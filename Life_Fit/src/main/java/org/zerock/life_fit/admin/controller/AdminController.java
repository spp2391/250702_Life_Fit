package org.zerock.life_fit.admin.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.zerock.life_fit.admin.dto.UserDTO;
import org.zerock.life_fit.admin.service.UserService;

@Controller
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;

    // 관리자 메인 패널
    @GetMapping("/panel")
    public String adminPanel() {
        return "admin/adminpanel";
    }

    // 회원관리 iframe 로딩용
    @GetMapping("/test")
    public String adminTestPage(@RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "10") int size,
                                Model model) {

        Page<UserDTO> userPage = userService.getUsersWithPaging(page, size);
        model.addAttribute("userPage", userPage);
        return "admin/admintest";
    }

    @GetMapping("/search")
    public String searchUsers(@RequestParam(required = false) String name,
                              @RequestParam(required = false) String role,
                              @RequestParam(defaultValue = "0") int page,
                              @RequestParam(defaultValue = "10") int size,
                              Model model) {
        Page<UserDTO> filteredUsers = userService.searchUsersWithPaging(name, role, page, size);
        model.addAttribute("userPage", filteredUsers);
        return "admin/admintest";
    }

    @PostMapping("/users/{userId}")
    public String updateUser(@PathVariable Long userId, @ModelAttribute UserDTO dto) {
        userService.updateUser(userId, dto);
        return "redirect:/api/admin/test";
    }

    @PostMapping("/users/{userId}/delete")
    public String deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return "redirect:/api/admin/test";
    }

    @PostMapping("/users/{userId}/role")
    public String changeRole(@PathVariable Long userId, @RequestParam String role) {
        userService.changeUserRole(userId, role);
        return "redirect:/api/admin/test";
    }

    @PostMapping("/users/{userId}/reset-password")
    public String resetPassword(@PathVariable Long userId) {
        userService.resetPassword(userId);
        return "redirect:/api/admin/test";
    }
}
