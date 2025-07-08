package org.zerock.life_fit.admin.service;

import org.springframework.data.domain.Page;
import org.zerock.life_fit.admin.dto.UserDTO;

import java.util.List;

public interface AdminService {
    List<UserDTO> getAllUsers();

    void updateUser(String userId, UserDTO dto);
    void deleteUser(String userId);
    void resetPassword(String userId);
    void changeUserRole(String userId, String role);
    Page<UserDTO> getUsersWithPaging(int page, int size);
    List<UserDTO> searchUsers(String email, String username, String role);
    Page<UserDTO> searchUsersWithPaging(String email, String username, String role, int page, int size);
}
