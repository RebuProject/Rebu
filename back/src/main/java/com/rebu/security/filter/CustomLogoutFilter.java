package com.rebu.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rebu.common.controller.dto.ApiResponse;
import com.rebu.common.util.RedisUtils;
import com.rebu.security.util.JWTUtil;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

@RequiredArgsConstructor
public class CustomLogoutFilter extends GenericFilterBean {

    private static final String PREFIX = "Refresh:";

    private final RedisUtils redisUtils;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        doFilter((HttpServletRequest) request, (HttpServletResponse) response, chain);
    }

    private void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        if (!requestURI.equals("/api/logout")) {
            chain.doFilter(request, response);
            return;
        }

        String requestMethod = request.getMethod();
        if (!requestMethod.equals("POST")) {
            chain.doFilter(request, response);
            return;
        }

        String refreshToken = null;
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("refresh")) {
                refreshToken = cookie.getValue();
            }
        }

        if (refreshToken == null) {
            setResponse(response, "리프레시 에러코드");
            return;
        }

        try {
            JWTUtil.isExpired(refreshToken);
        } catch (ExpiredJwtException e) {
            setResponse(response, "리프레시 에러코드");
            return;
        }

        String category = JWTUtil.getCategory(refreshToken);
        if (!category.equals("refresh")) {
            setResponse(response, "리프레시 에러코드");
            return;
        }

        String nickname = JWTUtil.getNickname(refreshToken);
        boolean isExist = redisUtils.existData(generatePrefixedKey(nickname));
        if (!isExist) {
            setResponse(response, "리프레시 에러코드");
            return;
        }

        redisUtils.deleteData(generatePrefixedKey(nickname));

        Cookie cookie = new Cookie("refresh", null);
        cookie.setMaxAge(0);
        cookie.setPath("/");

        response.addCookie(cookie);
        setResponse(response, "로그아웃 성공");

    }

    private void setResponse(HttpServletResponse response, String code) throws IOException {
        response.setStatus(HttpStatus.OK.value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        ApiResponse<?> apiResponse = new ApiResponse<>(code, null);
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonResponse = objectMapper.writeValueAsString(apiResponse);
        response.getWriter().write(jsonResponse);
    }

    private String generatePrefixedKey(String key) {
        return PREFIX + key;
    }
}