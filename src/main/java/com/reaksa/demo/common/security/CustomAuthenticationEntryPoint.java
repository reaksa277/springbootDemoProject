package com.reaksa.demo.common.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.reaksa.demo.dto.base.Response;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException, ServletException {
        // handle custom exception response
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401

        Response errResponse = Response.error("401", "fail", "Authentication required, " +
                "Please provide a valid JWT token");

        response.getWriter().write(objectMapper.writeValueAsString(errResponse));
    }
}
