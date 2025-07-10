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
    public User register(UserRegisterRequest dto) throws Exception {
        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }
        User user = User.builder()
                .email(dto.getEmail())
                .name(dto.getName())
                .password(passwordEncoder.encode(dto.getPassword()))
                .nickname(dto.getNickname())
                .build();
        return userRepository.save(user);
    }

    public UserProfileResponse getProfile(String userId) {
        return userRepository.findByEmail(userId)
                .map(UserProfileResponse::fromEntity)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
    }

    // 회원정보 수정
    @Transactional
    public UserProfileResponse updateUser(String loginId, UserRegisterRequest dto) {
        User user = userRepository.findById(dto.getUserId()).orElseThrow();
        if (!user.getEmail().equals(loginId)) {
            throw new IllegalArgumentException("권한이 없습니다.");
        }
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
