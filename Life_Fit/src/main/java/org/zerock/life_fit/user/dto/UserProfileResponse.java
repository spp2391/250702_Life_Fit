package org.zerock.life_fit.user.dto;

import lombok.*;
import org.zerock.life_fit.user.domain.User;

import java.util.List;

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
    private String phoneNumber;
    List<FavoriteDTO> favoriteList;

    public static UserProfileResponse fromEntity(User user) {
        return UserProfileResponse.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .regdate(user.getRegdate())
                .moddate(user.getModdate())
                .phoneNumber(user.getPhoneNumber())
                .name(user.getName())
                .favoriteList(
                        user.getFavorites().stream().map(favorite -> FavoriteDTO.builder()
                                .id(favorite.getId())
                                .title(favorite.getTitle())
                                .user_id(favorite.getUser().getUserId().toString())
                                .description(favorite.getDescription())
                                .address(favorite.getAddress())
                                .regdate(favorite.getRegdate() != null ? favorite.getRegdate().toString() : "")
                                .url(favorite.getUrl())
                                .lat(favorite.getLat() != null ? favorite.getLat() : 0.0)
                                .lng(favorite.getLng() != null ? favorite.getLng() : 0.0)
                                .build()
                        ).toList()
                )
                .build();
    }
}
