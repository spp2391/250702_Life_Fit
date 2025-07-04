package org.zerock.life_fit.admin.service;

import org.zerock.life_fit.admin.dto.UserDTO;

import java.util.List;

public interface AdminService {
    List<UserDTO> getAllUsers();
    void updateUser(String userId, UserDTO dto);
    void deleteUser(String userId);
    void resetPassword(String userId);
    void changeUserRole(String userId, String role);

    // 🔍 검색 기능
    List<UserDTO> searchUsers(String userId, String username, String role);
}
