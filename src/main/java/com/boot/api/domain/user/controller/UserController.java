package com.boot.api.domain.user.controller;

import com.boot.api.domain.user.dto.CreateUserDto;
import com.boot.api.domain.user.dto.FindUserListDto;
import com.boot.api.domain.user.dto.LoginDto;
import com.boot.api.domain.user.entity.User;
import com.boot.api.domain.user.service.UserService;
import com.boot.api.domain.user.vo.FindUserListResultVo;
import com.boot.api.domain.user.vo.LoginVo;
import com.boot.api.globals.annotations.auth.ValidateNonLogin;
import com.boot.api.globals.annotations.pagination.Pagination;
import com.boot.api.globals.annotations.role.RequireRole;
import com.boot.api.globals.auth.JwtTokenProvider;
import com.boot.api.globals.auth.TokenInfo;
import com.boot.api.globals.common.enums.Role;
import com.boot.api.globals.response.BasePaginationResponse;
import com.boot.api.globals.response.BaseResponse;
import com.boot.api.globals.session.UserSessionInfo;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.boot.api.globals.session.UserSessionHolder.getUserSession;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    /**
     * 사용자 조회
     * @param findUserListDto
     * @param pageable
     * @return BaseResponse<BasePaginationResponse<FindUserListResultVo>>
     */
    @GetMapping("/user")
    public ResponseEntity<BaseResponse<BasePaginationResponse<FindUserListResultVo>>> findUserList(FindUserListDto findUserListDto, @Pagination Pageable pageable) {
        UserSessionInfo userSessionInfo = getUserSession();

        return ResponseEntity.ok(BaseResponse.successWithData(userService.findUserList(findUserListDto, userSessionInfo, pageable)));
    }

    /**
     * 사용자 단일 조회
     * @param id
     * @return BaseResponse<User>
     */
    @GetMapping("/user/{id}")
    public ResponseEntity<BaseResponse<User>> findUserById(@PathVariable Integer id) {
        return ResponseEntity.ok(BaseResponse.successWithData(userService.findUserById(id)));
    }

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

    /**
     * 사용자 생성
     * @param createUserDto
     * @return
     */
    @PostMapping("/user")
    @ValidateNonLogin
    public ResponseEntity<Void> createUser(@RequestBody @Valid CreateUserDto createUserDto) {
        UserSessionInfo userSessionInfo = getUserSession();
        userService.createUser(createUserDto, userSessionInfo);

        return ResponseEntity.ok().build();
    }

    /**
     * RefreshToken 재발급
     * @param refreshToken
     * @param response
     * @return BaseResponse<LoginVo>
     */
    @PostMapping("/auth/refresh")
    public ResponseEntity<BaseResponse<LoginVo>> refreshToken(@CookieValue(name = "api_rt") String refreshToken, HttpServletResponse response) {
        TokenInfo tokenInfo = jwtTokenProvider.tokenRefresh(refreshToken);
        final Cookie refreshTokenCookie = jwtTokenProvider.tokenCookie(tokenInfo.getRefreshToken());
        response.addCookie(refreshTokenCookie);

        return ResponseEntity.ok(BaseResponse.successWithData(LoginVo.builder().accessToken(tokenInfo.getAccessToken()).build()));
    }

    /**
     * 사용자 비활성화
     * @param id
     * @return
     */
    @PatchMapping("/user/{id}")
    @RequireRole(Role.SUPER)
    public ResponseEntity<Void> disabledUser(@PathVariable Integer id) {
        userService.disabledUser(id);

        return ResponseEntity.ok().build();
    }

    /**
     * 사용자 삭제
     * @param id
     * @return
     */
    @DeleteMapping("/user/{id}")
    @RequireRole(Role.SUPER)
    public ResponseEntity<Void> deletedUser(@PathVariable Integer id) {
        userService.deletedUser(id);

        return ResponseEntity.ok().build();
    }
}
