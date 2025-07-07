package org.zerock.life_fit.user.domain;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity
    @Table(name = "user")
    @Getter
    @Setter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    @Builder
    public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", length = 100, nullable = false)
    private Long userId; // 사용자 ID (PK)

    @Column(name = "name", length = 100, nullable = false)
    private String name; // 사용자 이름

    @Column(name = "email", length = 255, nullable = false, unique = true)
    private String email; // 이메일 주소

    @Column(name = "password", length = 255)
    private String password; // 비밀번호

    @Column(name = "phone_number", length = 15)
    private String phoneNumber; // 전화번호

    @Column(name = "role", length = 10)
    private String role; // 사용자 역할 (ex: USER, ADMIN)

    @Column(name = "regdate")
    private LocalDateTime regdate; // 등록일

    @Column(name = "moddate")
    private LocalDateTime moddate; // 수정일

    @Column(name = "nickname", length = 15)
    private String nickname; // 닉네임

    // 회원 탈퇴 여부
    private boolean deleted = false;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("USER"));
    }
    @Override
    public String getUsername() {
        return email;
    }

    @PrePersist
    public void prePersist() {
            this.regdate = LocalDateTime.now();
        }
}
