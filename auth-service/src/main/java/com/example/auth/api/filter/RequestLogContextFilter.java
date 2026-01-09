package com.example.auth.api.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
public class RequestLogContextFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        // ===== Generate trace id =====
        String traceId = UUID.randomUUID().toString();

        // ===== Put structured context =====
        MDC.put("traceId", traceId);
        MDC.put("service", "auth-service");
        MDC.put("path", request.getRequestURI());
        MDC.put("method", request.getMethod());

        long startTime = System.currentTimeMillis();

        try {
            filterChain.doFilter(request, response);
        } finally {
            long durationMs = System.currentTimeMillis() - startTime;
            MDC.put("durationMs", String.valueOf(durationMs));

            // IMPORTANT: clear MDC to avoid memory leak
            MDC.clear();
        }
    }
}
