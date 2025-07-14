package org.zerock.life_fit.admin.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.zerock.life_fit.admin.dto.UserDTO;
import org.zerock.life_fit.admin.repository.AdminUserRepository;
import org.zerock.life_fit.user.domain.User;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminUserService {

    private final AdminUserRepository adminUserRepository;

    public List<UserDTO> getAllUsers() {
        return adminUserRepository.findAll().stream()
                .map(UserDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public void updateUser(Long userId, UserDTO dto) {
        Optional<User> optionalUser = adminUserRepository.findById(userId);
        optionalUser.ifPresent(user -> {
            user.setName(dto.getName());
            user.setEmail(dto.getEmail());
            user.setPhoneNumber(dto.getPhoneNumber());
            user.setNickname(dto.getNickname());
            adminUserRepository.save(user);
        });
    }

    public void deleteUser(Long userId) {
        adminUserRepository.deleteById(userId);
    }

    public void resetPassword(Long userId) {
        adminUserRepository.findById(userId).ifPresent(user -> {
            user.setPassword("1234"); // 암호화 필요
            adminUserRepository.save(user);
        });
    }

    public void changeUserRole(Long userId, String role) {
        adminUserRepository.findById(userId).ifPresent(user -> {
            user.setRole(role);
            adminUserRepository.save(user);
        });
    }

    public Page<UserDTO> getUsersWithPaging(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return adminUserRepository.findAll(pageable).map(UserDTO::fromEntity);
    }

    public List<UserDTO> searchUsers(String email, String nickname, String role) {
        return adminUserRepository.searchUsers(email, nickname, role).stream()
                .map(UserDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public Page<UserDTO> searchUsersWithPaging(String name, String role, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return adminUserRepository.searchUsersWithPaging(name, role, pageable)
                .map(UserDTO::fromEntity);
    }
}
