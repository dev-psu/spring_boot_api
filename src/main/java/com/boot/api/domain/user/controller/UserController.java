package com.boot.api.domain.user.controller;

import com.boot.api.domain.user.dto.LoginDto;
import com.boot.api.domain.user.repository.UserRepository;
import com.boot.api.domain.user.service.UserService;
import com.boot.api.domain.user.vo.LoginVo;
import com.boot.api.globals.annotations.ValidateNonLogin;
import com.boot.api.globals.auth.JwtTokenProvider;
import com.boot.api.globals.auth.TokenInfo;
import com.boot.api.globals.response.BaseResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    /**
     * 로그인
     * @param loginDto
     * @param response
     * @return BaseResponse<LoginVo>
     */
    @PostMapping("/auth/login")
    @ValidateNonLogin
    public ResponseEntity<BaseResponse<LoginVo>> login(@RequestBody LoginDto loginDto, HttpServletResponse response) {
        TokenInfo tokenInfo = jwtTokenProvider.createToken(userService.login(loginDto));

        final Cookie refreshTokenCookie = jwtTokenProvider.tokenCookie(tokenInfo.getRefreshToken());
        response.addCookie(refreshTokenCookie);

        return ResponseEntity.ok(BaseResponse.successWithData(LoginVo.builder().accessToken(tokenInfo.getAccessToken()).build()));
    }

    @PostMapping("/auth/refresh")
    public ResponseEntity<BaseResponse<LoginVo>> refreshToken(@CookieValue(name = "api_rt") String refreshToken, HttpServletResponse response) {
        TokenInfo tokenInfo = jwtTokenProvider.tokenRefresh(refreshToken);
        final Cookie refreshTokenCookie = jwtTokenProvider.tokenCookie(tokenInfo.getRefreshToken());
        response.addCookie(refreshTokenCookie);

        return ResponseEntity.ok(BaseResponse.successWithData(LoginVo.builder().accessToken(tokenInfo.getAccessToken()).build()));
    }


}
