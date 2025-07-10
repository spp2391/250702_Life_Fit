package org.zerock.life_fit.admin.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.zerock.life_fit.admin.dto.UserDTO;
import org.zerock.life_fit.admin.repository.UserRepository;
import org.zerock.life_fit.user.domain.User;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(UserDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public void updateUser(Long userId, UserDTO dto) {
        Optional<User> optionalUser = userRepository.findById(userId);
        optionalUser.ifPresent(user -> {
            user.setName(dto.getName());
            user.setEmail(dto.getEmail());
            user.setPhoneNumber(dto.getPhoneNumber());
            user.setNickname(dto.getNickname());
            userRepository.save(user);
        });
    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    public void resetPassword(Long userId) {
        userRepository.findById(userId).ifPresent(user -> {
            user.setPassword("1234"); // 암호화 필요
            userRepository.save(user);
        });
    }

    public void changeUserRole(Long userId, String role) {
        userRepository.findById(userId).ifPresent(user -> {
            user.setRole(role);
            userRepository.save(user);
        });
    }

    public Page<UserDTO> getUsersWithPaging(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return userRepository.findAll(pageable).map(UserDTO::fromEntity);
    }

    public List<UserDTO> searchUsers(String email, String nickname, String role) {
        return userRepository.searchUsers(email, nickname, role).stream()
                .map(UserDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public Page<UserDTO> searchUsersWithPaging(String name, String role, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return userRepository.searchUsersWithPaging(name, role, pageable)
                .map(UserDTO::fromEntity);
    }
}
