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
public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(UserDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void updateUser(String userId, UserDTO dto) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setName(dto.getName());
            user.setEmail(dto.getEmail());
            user.setPhoneNumber(dto.getPhoneNumber());
            user.setNickname(dto.getNickname());
            userRepository.save(user);
        }
    }

    @Override
    public void deleteUser(String userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public void resetPassword(String userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        optionalUser.ifPresent(user -> {
            user.setPassword("1234"); // 실제로는 암호화 필요
            userRepository.save(user);
        });
    }

    @Override
    public void changeUserRole(String userId, String role) {
        Optional<User> optionalUser = userRepository.findById(userId);
        optionalUser.ifPresent(user -> {
            user.setRole(role);
            userRepository.save(user);
        });
    }

    @Override
    public List<UserDTO> searchUsers(String email, String username, String role) {
        return userRepository.searchUsers(email, username, role).stream()
                .map(UserDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public Page<UserDTO> getUsersWithPaging(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return userRepository.findAll(pageable).map(UserDTO::fromEntity);
    }

    @Override
    public Page<UserDTO> searchUsersWithPaging(String email, String name, String role, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return userRepository.searchUsersWithPaging(name, role, pageable)
                .map(UserDTO::fromEntity);
    }

}
