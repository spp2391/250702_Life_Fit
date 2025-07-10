package org.zerock.life_fit.user.dto;

import lombok.*;
import org.zerock.life_fit.user.domain.User;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProfileResponse {
    private Long userId;
    private String email;
    private String nickname;
    private java.time.LocalDateTime regdate;
    private java.time.LocalDateTime moddate;
    private String name;

    public static UserProfileResponse fromEntity(User user) {
        return UserProfileResponse.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .regdate(user.getRegdate())
                .moddate(user.getModdate())
                .name(user.getName())
                .build();
    }
}
