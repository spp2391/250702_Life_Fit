package org.zerock.life_fit.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.zerock.life_fit.user.domain.User;
import org.zerock.life_fit.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;  // JPA 리포지토리

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

        /*return User.builder()
                .username(user.getEmail())
                .password(user.getPassword()) // 암호화된 비밀번호
                .role(user.getRole())       // "USER" 혹은 "ADMIN"
                .build();*/
    }
}

