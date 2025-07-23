package org.zerock.life_fit.OAuth2User;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.zerock.life_fit.user.domain.User;
import org.zerock.life_fit.user.dto.UserProfileResponse;
import org.zerock.life_fit.user.service.UserService;

import java.io.IOException;

@Component
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String pw = ((CustomOAuth2User) oAuth2User).getUser().getPassword();
        if(pw == null || pw.isEmpty()){
            response.sendRedirect("/member/profile");
            return;
        }
        response.sendRedirect("/");
    }
}
