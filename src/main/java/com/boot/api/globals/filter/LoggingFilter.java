package com.boot.api.globals.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Component
@Slf4j
public class LoggingFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        MDC.put("traceId", String.format("%s", UUID.randomUUID()));
        if(isAsyncDispatch(request)) {
            filterChain.doFilter(request, response);
        } else {

        }
    }

    protected void doFilterWrapped(RequestWrapper request, ContentCachingResponseWrapper response, FilterChain filterChain) {
        try {

        } finally {

        }
    }

    private void logRequest(RequestWrapper request) throws IOException {
        String queryString = request.getQueryString();
        log.info("[{} {}][{}]",
            request.getMethod(),
            request.getRequestURI(),
            queryString);

        String content = getContent(request.getInputStream());
        if(StringUtils.hasText(content)) {
            log.info("Request Payload - {}", content);
        }

    }

    private void logResponse(ContentCachingResponseWrapper response) throws IOException {
        int status = response.getStatus();

        log.info("Response Status - {}", status);

        if(status != 200) {
            log.info("Response Payload - {}", getContent(response.getContentInputStream()));
        }
    }

    private String getContent(InputStream inputStream) throws IOException {
        byte[] content = StreamUtils.copyToByteArray(inputStream);
        if(content.length > 0) {
            return new String(content);
        } else {
            return null;
        }
    }
}
