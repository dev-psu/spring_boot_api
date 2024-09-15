package com.boot.api.globals.session;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserSessionHolder {
    private static final ThreadLocal<UserSessionInfo> userSessionThreadLocal = new ThreadLocal<>();

    public static void setUserSession(UserSessionInfo userSession) {
        userSessionThreadLocal.set(userSession);
    }

    public static UserSessionInfo getUserSession() {
        return userSessionThreadLocal.get();
    }

    public static void clearUserSession() {
        userSessionThreadLocal.remove();
    }
}
