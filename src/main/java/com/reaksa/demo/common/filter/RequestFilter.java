package com.reaksa.demo.common.filter;

import com.reaksa.demo.common.constant.RequestConstant;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;

@Component
@Order(1)
public class RequestFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException,
            ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        try {
            String requestId = UUID.randomUUID().toString();
            MDC.put(RequestConstant.REQUEST_ID, requestId);

            String httpMethod = httpRequest.getMethod();

            String requestURI = httpRequest.getRequestURI();
            String requestQuery = httpRequest.getQueryString();
            String fullRequestPath = requestQuery != null ? requestURI.concat("?").concat(requestQuery) : requestURI;

            MDC.put(RequestConstant.HTTP_METHOD, httpMethod);
            MDC.put(RequestConstant.REQUEST_PATH, fullRequestPath);

            // execute our implementation / logic
            filterChain.doFilter(servletRequest, servletResponse);
        } finally {
            MDC.clear();
        }
    }
}
