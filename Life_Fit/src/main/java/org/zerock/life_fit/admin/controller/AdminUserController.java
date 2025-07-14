package org.zerock.life_fit.admin.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.zerock.life_fit.admin.dto.UserDTO;
import org.zerock.life_fit.admin.service.AdminUserService;

@Controller
@RequestMapping("/api/admin/users")
@RequiredArgsConstructor
public class AdminUserController {

    private final AdminUserService adminUserService;

    // 관리자 패널 페이지 또는 iframe 본문만 렌더링
    @GetMapping("/test")
    public String showAdminPanel(Model model,
                                 @RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "10") int size,
                                 @RequestParam(value = "embed", required = false) Boolean embed) {
        Page<UserDTO> userPage = adminUserService.getUsersWithPaging(page, size);
        model.addAttribute("userPage", userPage);

        if (Boolean.TRUE.equals(embed)) {
            return "admin/admintest"; // 본문만 (iframe)
        } else {
            return "admin/adminpanel"; // 전체 페이지 (사이드바 포함)
        }
    }

    // 유저 전체 리스트 (iframe 내부용)
    @GetMapping
    public String listUsers(@RequestParam(defaultValue = "0") int page,
                            @RequestParam(defaultValue = "10") int size,
                            Model model) {
        Page<UserDTO> userPage = adminUserService.getUsersWithPaging(page, size);
        model.addAttribute("userPage", userPage);
        return "admin/admintest";
    }

    // 유저 검색
    @GetMapping("/search")
    public String searchUsers(@RequestParam(required = false) String name,
                              @RequestParam(required = false) String role,
                              @RequestParam(defaultValue = "0") int page,
                              @RequestParam(defaultValue = "10") int size,
                              Model model) {
        Page<UserDTO> filteredUsers = adminUserService.searchUsersWithPaging(name, role, page, size);
        model.addAttribute("userPage", filteredUsers);
        return "admin/admintest";
    }

    // 유저 정보 수정
    @PostMapping("/{userId}")
    public String updateUser(@PathVariable Long userId, @ModelAttribute UserDTO dto) {
        adminUserService.updateUser(userId, dto);
        return "redirect:/api/admin/users/test?embed=true";
    }

    // 유저 삭제
    @PostMapping("/{userId}/delete")
    public String deleteUser(@PathVariable Long userId) {
        adminUserService.deleteUser(userId);
        return "redirect:/api/admin/users/test?embed=true";
    }

    // 권한 변경
    @PostMapping("/{userId}/role")
    public String changeRole(@PathVariable Long userId, @RequestParam String role) {
        adminUserService.changeUserRole(userId, role);
        return "redirect:/api/admin/users/test?embed=true";
    }

    // 비밀번호 초기화
    @PostMapping("/{userId}/reset-password")
    public String resetPassword(@PathVariable Long userId) {
        adminUserService.resetPassword(userId);
        return "redirect:/api/admin/users/test?embed=true";
    }
}
