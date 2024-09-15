package com.boot.api.globals.interceptor;

import com.boot.api.domain.user.entity.User;
import com.boot.api.domain.user.repository.UserRepository;
import com.boot.api.globals.annotations.ValidateNonLogin;
import com.boot.api.globals.auth.JwtTokenProvider;
import com.boot.api.globals.common.enums.ErrorCode;
import com.boot.api.globals.common.enums.TokenType;
import com.boot.api.globals.error.exception.BusinessException;
import com.boot.api.globals.error.exception.EntityNotFoundException;
import com.boot.api.globals.session.UserSessionHolder;
import com.boot.api.globals.session.UserSessionInfo;
import com.boot.api.globals.utils.CookieUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class AuthenticationInterceptor implements HandlerInterceptor {
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final CookieUtil cookieUtil;

    //Todo ParameterStore
    private static final String REFRESH_TOKEN_KEY = "api_rt";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HandlerMethod handlerMethod;
        ValidateNonLogin validateNonLogin;

        try {
            handlerMethod = (HandlerMethod) handler;
            validateNonLogin = handlerMethod.getMethodAnnotation(ValidateNonLogin.class);
        } catch (ClassCastException e) {
            return true;
        }

        if(validateNonLogin != null) {
            return true;
        }

        String accessToken = jwtTokenProvider.findToken();

        if(accessToken == null) {
            deletedJwt(response);
        }

        try {
            jwtTokenProvider.validateToken(accessToken, TokenType.ACCESS);
        } catch (Exception e) {
            deletedJwt(response);
        }

        String refreshToken = cookieUtil.retrieveCookie(request, REFRESH_TOKEN_KEY);

        if(refreshToken == null)
            throw new BusinessException(ErrorCode.HANDLE_ACCESS_DENIED);

        try {
            jwtTokenProvider.validateToken(refreshToken, TokenType.REFRESH);
        } catch (Exception e) {
            deletedJwt(response);
        }

        String userId = jwtTokenProvider.extractSubFromToken(accessToken, TokenType.ACCESS);
        User user = userRepository.findByUserEmail(userId).orElseThrow(() -> new EntityNotFoundException(userId, ErrorCode.USER_NOT_FOUND));

        UserSessionInfo userSessionInfo = UserSessionInfo.builder()
            .id(user.getId())
            .userEmail(user.getUserEmail())
            .build();

        HttpSession session = request.getSession();

        if(session.getAttribute("userSession") == null)
            session.setAttribute("userSession", userSessionInfo);

        UserSessionHolder.setUserSession(userSessionInfo);

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    private void deletedJwt(HttpServletResponse response) {
        final Cookie refreshTokenCookie = new Cookie(REFRESH_TOKEN_KEY, null);
        refreshTokenCookie.setSecure(true);
        refreshTokenCookie.setDomain(REFRESH_TOKEN_KEY);
        refreshTokenCookie.setMaxAge(0);
        refreshTokenCookie.setHttpOnly(true);
        response.addCookie(refreshTokenCookie);

        throw new BusinessException("", null);
    }
}
