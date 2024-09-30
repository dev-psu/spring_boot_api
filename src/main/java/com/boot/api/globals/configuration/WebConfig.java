package com.boot.api.globals.configuration;

import com.boot.api.domain.user.repository.UserRepository;
import com.boot.api.globals.annotations.pagination.PaginationArgumentResolver;
import com.boot.api.globals.auth.JwtTokenProvider;
import com.boot.api.globals.interceptor.AuthenticationInterceptor;
import com.boot.api.globals.utils.CookieUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@RequiredArgsConstructor
@EnableWebMvc
@Configuration
public class WebConfig implements WebMvcConfigurer {
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final CookieUtil cookieUtil;
    private final PaginationArgumentResolver paginationArgumentResolver;

    @Bean
    public AuthenticationInterceptor authenticationInterceptor() {
        return new AuthenticationInterceptor(jwtTokenProvider, userRepository, cookieUtil);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(paginationArgumentResolver);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authenticationInterceptor())
            .addPathPatterns("/**");
    }


}
