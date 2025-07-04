package org.zerock.life_fit.user.domain;


import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
    @Table(
            name = "user",
            uniqueConstraints = {
                    @UniqueConstraint(columnNames = "username"),
                    @UniqueConstraint(columnNames = "email")
            }
    )
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public class User {

        @Id
        @Column(name = "user_id", length = 100, nullable = false)
        private String userId; // 사용자 ID (PK)

        @Column(name = "username", length = 100, nullable = false, unique = true)
        private String username; // 사용자 이름

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
}
