package com.boot.api.globals.auth;

import com.boot.api.domain.user.entity.User;
import com.boot.api.domain.user.repository.UserRepository;
import com.boot.api.domain.user.service.UserService;
import com.boot.api.globals.common.enums.ErrorCode;
import com.boot.api.globals.common.enums.TokenType;
import com.boot.api.globals.error.exception.BusinessException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {
    private static final int ACCESS_TOKEN_EXPIRATION_TIME = 1000 * 60 * 60 * 10000 * 100000;
    private static final int REFRESH_TOKEN_EXPIRATION_TIME = 1000 * 60 * 60 * 10000 * 100000;
    private final RedisTemplate<String, Object> redisTemplate;
    private final SecretKey secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode("oek194qx28cnn12ijn52njk6jk62jkk2jkpu62iowefkj112jk"));
    private static final String REFRESH_TOKEN_KEY = "api_rt";
    private final UserService userService;

    public TokenInfo createToken(User user) {
        Date now = new Date();
        Date accessTokenExpiration = new Date(now.getTime() + ACCESS_TOKEN_EXPIRATION_TIME);
        Date refreshTokenExpiration = new Date(now.getTime() + REFRESH_TOKEN_EXPIRATION_TIME);

        String accessToken = Jwts.builder()
            .setSubject(REFRESH_TOKEN_KEY)
            .claim("id", user.getUserId())
            .claim("userId", user.getUserEmail())
            .claim("role", "")
            .setIssuedAt(now)
            .setExpiration(accessTokenExpiration)
            .signWith(secretKey)
            .compact();

        String refreshToken = Jwts.builder()
            .setSubject(REFRESH_TOKEN_KEY)
            .claim("id", user.getId())
            .claim("userId", user.getUserEmail())
            .claim("role", "")
            .setIssuedAt(now)
            .setExpiration(refreshTokenExpiration)
            .signWith(secretKey)
            .compact();

        redisTemplate.opsForValue().set(user.getUserEmail(), refreshToken, REFRESH_TOKEN_EXPIRATION_TIME, TimeUnit.MILLISECONDS);

        return new TokenInfo("Bearer ", accessToken, refreshToken);
    }

    public String extractSubFromToken(String token, TokenType tokenType) {
        try {
            Claims claims = getClaims(token, tokenType);
            return claims.get("id").toString();
        } catch (Exception e) {
            return null;
        }
    }

    public Boolean existsRefreshToken(String refreshToken, String id) {
        String savedRefreshToken = (String) redisTemplate.opsForValue().get(id);

        return refreshToken.equals(savedRefreshToken);
    }

    public String findToken() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader == null) {
            throw new BusinessException(ErrorCode.HANDLE_ACCESS_DENIED);
        }

        String[] tokenHeader = authorizationHeader.split(" ");

        return tokenHeader[1];
    }

    private Claims getClaims(String token, TokenType tokenType) {
        return Jwts.parserBuilder()
            .setSigningKey(secretKey)
            .build()
            .parseClaimsJws(token)
            .getBody();
    }

    public Boolean validateToken(String token, TokenType tokenType) {
        try {
            getClaims(token, tokenType);
            return true;
        } catch (Exception e) {
            if (e instanceof SecurityException) {
                System.out.println("SecurityException");
            } else if (e instanceof MalformedJwtException) {
                System.out.println("MalformedJwtException");
            } else if (e instanceof ExpiredJwtException) {
                System.out.println("ExpiredJwtException");
            } else if (e instanceof UnsupportedJwtException) {
                System.out.println("UnsupportedJwtException");
            } else if (e instanceof IllegalArgumentException) {
                System.out.println("IllegalArgumentException");
            } else {
                System.out.println("else");
            }
        }
        return false;

    }

    public void deleteToken(String refreshToken) {
        redisTemplate.delete(refreshToken);
    }

    public TokenInfo tokenRefresh(String refreshToken) {
        String currentAccessToken = this.findToken();

        if (currentAccessToken == null) {
            // Handle case where currentAccessToken is null
            return null;
        }

        String userEmail = this.extractSubFromToken(refreshToken, TokenType.REFRESH);
        User user = userService.findUserByUserEmail(userEmail);

        if (this.validateToken(refreshToken, TokenType.REFRESH) && this.existsRefreshToken(refreshToken, userEmail)) {
            return this.createToken(user);
        } else {
            // Handle invalid token case
            throw new BusinessException(ErrorCode.INVALID_TOKEN);
        }
    }

    public Cookie tokenCookie(String refreshToken) {
        final Cookie cookie = new Cookie("api_rt", refreshToken);
        cookie.setSecure(true);
        cookie.setDomain("");
        cookie.setMaxAge(789000);
        cookie.setHttpOnly(true);
        cookie.setPath("/");

        return cookie;
    }
}
