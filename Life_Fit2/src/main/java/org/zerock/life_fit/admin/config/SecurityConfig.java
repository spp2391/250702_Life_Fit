package org.zerock.life_fit.admin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // ✅ CSRF 비활성화 (람다 방식)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/admin/**").permitAll() // 관리자 페이지 전체 허용
                        .anyRequest().permitAll()
                )
                .formLogin(Customizer.withDefaults())  // ✅ 로그인 기본 설정 허용
                .logout(logout -> logout.logoutSuccessUrl("/login?logout")); // ✅ 로그아웃 설정

        return http.build();
    }
}
