package com.gustavo.ecommerce_api.security;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtFilter implements Filter {

    private final JwtService jwtService;

    public JwtFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;

        String path = req.getRequestURI();

        // libera login
        if (path.contains("/auth/login") ||
                path.contains("/swagger") ||
                path.contains("/v3/api-docs") ||
                path.contains("/swagger-ui")) {

            chain.doFilter(request, response);
            return;
        }

        String header = req.getHeader("Authorization");

        if (header == null || !header.startsWith("Bearer ")) {
            ((HttpServletResponse) response).setStatus(401);
            return;
        }

        String token = header.replace("Bearer ", "");

        if (!jwtService.tokenValido(token)) {
            ((HttpServletResponse) response).setStatus(401);
            return;
        }

        chain.doFilter(request, response);
    }
}