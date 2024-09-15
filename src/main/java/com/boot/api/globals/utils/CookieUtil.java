package com.boot.api.globals.utils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Slf4j
@RequiredArgsConstructor
public class CookieUtil {
    public String retrieveCookie(HttpServletRequest request, String cookieKey) {
        if(request.getCookies() == null)
            return null;
        return Arrays.stream(request.getCookies())
            .filter(c -> c.getName().equals(cookieKey))
            .findFirst()
            .map(Cookie::getValue)
            .orElse(null);
    }
}
