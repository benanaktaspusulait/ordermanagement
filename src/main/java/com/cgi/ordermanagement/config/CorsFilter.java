package com.cgi.ordermanagement.config;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CorsFilter implements Filter {


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpServletRequest request = (HttpServletRequest) servletRequest;

        response.setHeader("Access-Control-Allow-Origin", "*");
        if ("OPTIONS".equals(request.getMethod())) {
            response.setHeader("Access-Control-Allow-Methods", "POST, PATCH, PUT, GET, OPTIONS, DELETE");
            response.setHeader("Access-Control-Max-Age", "3600");
            response.setHeader("Access-Control-Allow-Headers", "authorization, content-type, Content-Length, X-Requested-With, X-Total-Count, Link, X-TenantID");
            response.setHeader("Access-Control-Expose-Headers", "X-Total-Count, Link, X-TenantID");
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }


}

