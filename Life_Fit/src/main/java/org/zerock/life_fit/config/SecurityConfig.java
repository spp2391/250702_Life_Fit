package org.zerock.life_fit.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.zerock.life_fit.OAuth2User.CustomOAuth2UserService_Naver;
import org.zerock.life_fit.OAuth2User.MultiOAuth2UserService;
import org.zerock.life_fit.admin.service.CustomUserDetailsService;
import org.zerock.life_fit.user.service.CustomOAuth2UserService_Kakao;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;
    private final CustomOAuth2UserService_Kakao customOAuth2UserServiceKakao;
    private final MultiOAuth2UserService multiOAuth2UserService;
    private final CustomOAuth2UserService_Naver customOAuth2UserServiceNaver;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .headers(headers -> headers
                        .frameOptions(frame -> frame.sameOrigin())
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/", "/css/**", "/js/**", "/images/**",
                                "/member/login", "/member/join",
                                "/free", "/topic", "/board/**","/comment/**"
                        ).permitAll()
                        .requestMatchers("/api/admin/**", "/admin/**").hasRole("ADMIN") // ✅ 추가
                        .requestMatchers("/member/profile", "/member/update", "/member/favorites", "/member/favorites/**")
                        .authenticated()
                        .anyRequest().authenticated()
                )

                .formLogin(form -> form
                        .loginPage("/member/login")
                        .defaultSuccessUrl("/mainscreen/main", true) // ✅ 수정됨: 로그인 성공 시 관리자 페이지로 이동
                        .failureUrl("/member/login?error=true")
                        .permitAll()
                )
                /*.oauth2Login(oauth2 -> oauth2
                        .loginPage("/member/login")
                        .userInfoEndpoint(userInfo -> userInfo.userService(customOAuth2UserService))
                        .defaultSuccessUrl("/mainscreen/main", true)
                )*/

                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/member/login")
                        .defaultSuccessUrl("/mainscreen/main", true)
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(multiOAuth2UserService)
                        )
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/member/login?logout")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                );

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authBuilder
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
        return authBuilder.build();
    }
}
