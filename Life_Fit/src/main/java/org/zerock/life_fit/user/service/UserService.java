package org.zerock.life_fit.user.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.zerock.life_fit.user.domain.User;
import org.zerock.life_fit.user.dto.UserLoginRequest;
import org.zerock.life_fit.user.dto.UserProfileResponse;
import org.zerock.life_fit.user.dto.UserRegisterRequest;
import org.zerock.life_fit.user.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // 회원가입, 로그인, 마이페이지
    @Transactional
    public User register(UserRegisterRequest dto) {
        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }
        User user = User.builder()
                .email(dto.getEmail())
                .username(dto.getUsername())
                .password(passwordEncoder.encode(dto.getPassword()))
                .nickname(dto.getNickname())
                .build();
        return userRepository.save(user);
    }

    public User login(UserLoginRequest dto) {
        return userRepository.findByEmail(dto.getEmail())
                .filter(u -> passwordEncoder.matches(dto.getPassword(), u.getPassword()))
                .orElseThrow(() -> new IllegalArgumentException("이메일 또는 비밀번호가 올바르지 않습니다."));
    }

    public UserProfileResponse getProfile(String userId) {
        return userRepository.findByUsername(userId)
                .map(UserProfileResponse::fromEntity)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
    }

    // 회원정보 수정
    @Transactional
    public UserProfileResponse updateUser(Long userId, UserRegisterRequest dto) {
        User user = userRepository.findById(userId).orElseThrow();
        user.setNickname(dto.getNickname());
        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
        }
        return UserProfileResponse.fromEntity(user);
    }

    @Transactional
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    public List<String> getFavorites(Long userId) {
        return List.of();
    }

    public boolean isAdmin(Long userId) {
        return userId == 1L;
    }
}
