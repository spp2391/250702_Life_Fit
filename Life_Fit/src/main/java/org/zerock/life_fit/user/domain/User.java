package org.zerock.life_fit.user.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "`user`") // user는 예약어이므로 백틱 처리
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @Column(name = "user_id", length = 100, nullable = false)
    private String userId;

    @Column(name = "deleted")
    private Boolean deleted;

    @Column(name = "email", length = 255, nullable = false, unique = true)
    private String email;

    @Column(name = "moddate")
    private LocalDateTime moddate;

    @Column(name = "name", length = 100)
    private String name;

    @Column(name = "nickname", length = 15)
    private String nickname;

    @Column(name = "password", length = 255)
    private String password;

    @Column(name = "phone_number", length = 15)
    private String phoneNumber;

    @Column(name = "regdate")
    private LocalDateTime regdate;

    @Column(name = "role", length = 10)
    private String role;

    @Column(name = "username")
    private String username;

}