package org.zerock.life_fit.OAuth2User;



import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.zerock.life_fit.user.domain.User;
import org.zerock.life_fit.user.repository.UserRepository;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        Map<String, Object> attributes = oAuth2User.getAttributes();

        // 네이버는 "response" 안에 사용자 정보가 있음
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");
        String mobile = (String) response.get("mobile");
        String email = (String) response.get("email");
        String name = (String) response.get("name");
        String nickname = (String) response.get("nickname");

        // DB에 사용자 존재 여부 확인
        User user = userRepository.findByEmail(email)
                .orElseGet(() -> {
                    // 새로 가입시키기
                    User newUser = User.builder()
                            .phoneNumber(mobile)
                            .email(email)
                            .name(name != null ? name : nickname)
                            .nickname(nickname != null ? nickname : name)
                            .role("USER")
                            .password("")  // 소셜 로그인은 비밀번호 없음
                            .build();
                    return userRepository.save(newUser);
                });

        return new CustomOAuth2User(user, attributes);
    }
}
