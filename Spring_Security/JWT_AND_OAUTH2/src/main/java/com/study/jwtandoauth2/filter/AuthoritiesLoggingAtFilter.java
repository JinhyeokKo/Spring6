package com.study.jwtandoauth2.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

import java.io.IOException;
import java.util.logging.Logger;

// AtFilter는 순서에 관계없이 실행
public class AuthoritiesLoggingAtFilter implements Filter {

    private final Logger LOG = Logger.getLogger(AuthoritiesLoggingAtFilter.class.getName());

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        LOG.info("Authentication Validation is in progress");
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
