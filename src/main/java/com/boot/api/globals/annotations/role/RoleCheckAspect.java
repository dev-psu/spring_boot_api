package com.boot.api.globals.annotations.role;

import com.boot.api.globals.common.enums.ErrorCode;
import com.boot.api.globals.common.enums.Role;
import com.boot.api.globals.error.exception.BusinessException;
import com.boot.api.globals.session.UserSessionInfo;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;

import static com.boot.api.globals.session.UserSessionHolder.getUserSession;

@Aspect
@Component
public class RoleCheckAspect {
    @Before("@annotation(com.boot.api.globals.annotations.role.RequireRole)")
    public void checkRole(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        RequireRole requireRole = method.getAnnotation(RequireRole.class);
        Role[] requiredRole = requireRole.value();

        UserSessionInfo userSessionInfo = getUserSession();

        if(userSessionInfo == null) {
            throw new BusinessException(ErrorCode.PERMISSION_DENIED);
        }

        boolean hasRequiredRole = Arrays.stream(requiredRole)
            .anyMatch(r -> r == userSessionInfo.getRole());

        if(!hasRequiredRole) {
            throw new BusinessException(ErrorCode.PERMISSION_DENIED);
        }
    }
}
