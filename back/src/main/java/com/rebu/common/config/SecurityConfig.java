package com.rebu.common.config;

import com.rebu.common.security.filter.AuthenticationFilter;
import com.rebu.common.security.filter.AuthorizationFilter;
import com.rebu.common.security.repository.RefreshTokenRepository;
import com.rebu.common.security.service.RefreshTokenService;
import com.rebu.common.security.util.JWTUtil;
import com.rebu.profile.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthenticationConfiguration authenticationConfiguration;
    private final JWTUtil jwtUtil;
    private final ProfileRepository profileRepository;
    private final RefreshTokenService refreshTokenService;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(AuthenticationConfiguration configuration) {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf((auth) -> auth.disable());
        http
                .formLogin((auth) -> auth.disable());
        http
                .httpBasic((auth) -> auth.disable());
        http
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/api/login", "/api/members").permitAll()
                        .requestMatchers("/api/refresh").permitAll()
                        .anyRequest().authenticated());
        http
                .addFilterBefore(new AuthorizationFilter(jwtUtil, profileRepository), AuthenticationFilter.class);
        http
                .addFilterAt(new AuthenticationFilter(authenticationManager(authenticationConfiguration), profileRepository, refreshTokenService, jwtUtil), UsernamePasswordAuthenticationFilter.class);
        http
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }
}
