package org.zerock.life_fit.admin.dto;

import lombok.*;
import org.zerock.life_fit.user.domain.User;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {

    private Long userId;
    private String name;
    private String nickname;
    private String email;
    private String phoneNumber;
    private String role;
    private LocalDateTime regdate;
    private LocalDateTime moddate;

    public static UserDTO fromEntity(User user) {
        return UserDTO.builder()
                .userId(user.getUserId())
                .name(user.getName())
                .nickname(user.getNickname())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .role(user.getRole())
                .regdate(user.getRegdate())
                .moddate(user.getModdate())
                .build();
    }
}
