package com.rebu.common.security.service;

import com.rebu.common.security.entity.RefreshToken;
import com.rebu.common.security.exception.RefreshInvalidException;
import com.rebu.common.security.repository.RefreshTokenRepository;
import com.rebu.common.security.util.JWTUtil;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final JWTUtil jwtUtil;

    @Transactional
    public void reissue(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = null;
        Cookie[] cookies = request.getCookies();

        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("refresh")) {
                refreshToken = cookie.getValue();
            }
        }

        if (refreshToken == null) {
            throw new RefreshInvalidException();
        }

        try {
            jwtUtil.isExpired(refreshToken);
        } catch (ExpiredJwtException e) {
            throw new RefreshInvalidException();
        }

        String category = jwtUtil.getCategory(refreshToken);
        if (!category.equals("refresh")) {
            throw new RefreshInvalidException();
        }

        Boolean isExist = refreshTokenRepository.existsByRefreshToken(refreshToken);
        if (!isExist) {
            throw new RefreshInvalidException();
        }

        String nickname = jwtUtil.getNickname(refreshToken);
        String type = jwtUtil.getType(refreshToken);

        String newAccess = jwtUtil.createJWT("access", nickname, type, 600000L);
        String newRefresh = jwtUtil.createJWT("refresh", nickname, type, 86400000L);

        refreshTokenRepository.deleteByRefreshToken(refreshToken);
        saveRefreshToken(nickname, newRefresh, 86400000L);

        response.setHeader("access", newAccess);
        response.addCookie(createCookie("refresh", newRefresh));
    }

    @Transactional
    public void saveRefreshToken(String nickname, String refresh, Long expired) {
        Date date = new Date(System.currentTimeMillis() + expired);

        RefreshToken refreshToken = RefreshToken.builder()
                .nickname(nickname)
                .refreshToken(refresh)
                .expiration(date.toString())
                .build();

        refreshTokenRepository.save(refreshToken);
    }

    private Cookie createCookie(String key, String value) {
        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(24*60*60);
        cookie.setHttpOnly(true);

        return cookie;
    }
}
