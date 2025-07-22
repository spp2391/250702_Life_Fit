package org.zerock.life_fit.user.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.zerock.life_fit.user.domain.User;
import org.zerock.life_fit.user.dto.UserProfileResponse;
import org.zerock.life_fit.user.dto.UserRegisterRequest;
import org.zerock.life_fit.user.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // 회원가입
    @Transactional
    public User register(UserRegisterRequest dto) throws Exception {
        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }

        // UserService.register 내부에서
        User user = User.builder()
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .nickname(dto.getNickname())
                .name(dto.getName())
                .phoneNumber(dto.getPhoneNumber())
                .role("USER") // ✅ 기본값 설정
                .build();


        return userRepository.save(user);
    }

    // 프로필 조회 (이메일 기반)
    public UserProfileResponse getProfile(String email) {
        return userRepository.findByEmail(email)
                .map(UserProfileResponse::fromEntity)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
    }

    // 회원정보 수정
    @Transactional
    public UserProfileResponse updateUser(String email, UserRegisterRequest dto) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        user.setNickname(dto.getNickname());

        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        return UserProfileResponse.fromEntity(user);
    }

    // 회원 탈퇴
    @Transactional
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    // 즐겨찾기 목록 조회 (미사용)
    public List<String> getFavorites(Long userId) {
        return List.of();
    }

    // 관리자 여부 확인
    public boolean isAdmin(Long userId) {
        return userId == 1L;
    }
}
