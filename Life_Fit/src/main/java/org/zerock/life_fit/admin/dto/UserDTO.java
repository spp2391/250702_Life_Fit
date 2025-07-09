package org.zerock.life_fit.admin.dto;

import lombok.*;
import org.zerock.life_fit.user.domain.User;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private String userId;
    private String username;
    private String name;
    private String nickname;
    private String email;
    private String phoneNumber;
    private String role;
    private LocalDateTime regdate;
    private LocalDateTime moddate;

    public static UserDTO fromEntity(User user) {
        return new UserDTO(
                user.getUserId(),
                user.getUsername(),
                user.getName(),
                user.getNickname(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getRole(),
                user.getRegdate(),
                user.getModdate()
        );
    }
}
