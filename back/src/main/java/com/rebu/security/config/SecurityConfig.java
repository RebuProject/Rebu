package com.rebu.security.config;

import com.rebu.common.service.RedisService;
import com.rebu.profile.repository.ProfileRepository;
import com.rebu.security.filter.AuthenticationFilter;
import com.rebu.security.filter.AuthorizationFilter;
import com.rebu.security.filter.CustomLogoutFilter;
import com.rebu.security.service.RefreshTokenService;
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
import org.springframework.security.web.authentication.logout.LogoutFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthenticationConfiguration authenticationConfiguration;
    private final ProfileRepository profileRepository;
    private final RefreshTokenService refreshTokenService;
    private final RedisService redisService;

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
                        .requestMatchers("/login", "/members").permitAll()
                        .requestMatchers("/auths/refresh").permitAll()
                        .requestMatchers("/auths/email/*", "/auths/phone/*", "/profiles/check-nickname", "/profiles/check-phone", "/members/check-email", "/members/*/password", "/members/find-email").permitAll()
                        .anyRequest().authenticated());
        http
                .addFilterBefore(new AuthorizationFilter(profileRepository), AuthenticationFilter.class);
        http
                .addFilterBefore(new CustomLogoutFilter(redisService), LogoutFilter.class);
        http
                .addFilterAt(new AuthenticationFilter(authenticationManager(authenticationConfiguration), profileRepository, refreshTokenService), UsernamePasswordAuthenticationFilter.class);
        http
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }
}
