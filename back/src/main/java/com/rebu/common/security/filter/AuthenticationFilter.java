package com.rebu.common.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rebu.common.controller.dto.ApiResponse;
import com.rebu.common.security.dto.CustomUserDetails;
import com.rebu.common.security.entity.RefreshToken;
import com.rebu.common.security.repository.RefreshTokenRepository;
import com.rebu.common.security.service.RefreshTokenService;
import com.rebu.common.security.util.JWTUtil;
import com.rebu.member.controller.dto.MemberLoginRequest;
import com.rebu.member.exception.StatusDeletedException;
import com.rebu.member.exception.StatusDormantException;
import com.rebu.profile.entity.Profile;
import com.rebu.profile.repository.ProfileRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final ProfileRepository profileRepository;
    private final RefreshTokenService refreshTokenService;
    private final JWTUtil jwtUtil;

    public AuthenticationFilter(AuthenticationManager authenticationManager, ProfileRepository profileRepository, RefreshTokenService refreshTokenService , JWTUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.profileRepository = profileRepository;
        this.refreshTokenService = refreshTokenService;
        this.jwtUtil = jwtUtil;
        setFilterProcessesUrl("/api/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        MemberLoginRequest loginDto = new MemberLoginRequest();

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            ServletInputStream inputStream = request.getInputStream();
            String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
            loginDto = objectMapper.readValue(messageBody, MemberLoginRequest.class);

        } catch (IOException e) {
            throw new RuntimeException();
        }

        String email = loginDto.getEmail();
        String password = loginDto.getPassword();

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(email, password, null);

        return authenticationManager.authenticate(authToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        CustomUserDetails userDetails = (CustomUserDetails) authResult.getPrincipal();

        Collection<? extends GrantedAuthority> authorities = authResult.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();
        String status = auth.getAuthority();

        if (status.equals("DORMANT")) {
            throw new StatusDormantException();
        } else if (status.equals("DELETED")) {
            throw new StatusDeletedException();
        }

        Profile profile = profileRepository.findFirstByEmailOrderByRecentTimeDesc(userDetails.getEmail());

        String nickname = profile.getNickname();
        String type = profile.getType().toString();

        String access = jwtUtil.createJWT("access", nickname, type, 600000L);
        String refresh = jwtUtil.createJWT("refresh", nickname, type, 86400000L);

        refreshTokenService.saveRefreshToken(nickname, refresh, 86400000L);

        response.setHeader("access", access);
        response.addCookie(createCookie("refresh", refresh));
        response.setStatus(HttpStatus.OK.value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        ApiResponse<?> apiResponse = new ApiResponse<>("로그인 성공 코드", null);
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonResponse = objectMapper.writeValueAsString(apiResponse);
        response.getWriter().write(jsonResponse);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpStatus.OK.value());

        ApiResponse<?> apiResponse = new ApiResponse<>("로그인 에러 코드", null);
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonResponse = objectMapper.writeValueAsString(apiResponse);
        response.getWriter().write(jsonResponse);
    }

    private Cookie createCookie(String key, String value) {
        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(24*60*60);
        cookie.setHttpOnly(true);

        return cookie;
    }
}
