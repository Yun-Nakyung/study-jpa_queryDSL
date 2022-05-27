package com.edu.signup.jwt;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

public class JwtAuthenticationFilter extends GenericFilterBean {

    private final JwtTokenProvider jwtTokenProvider;

    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        // 헤더에서 JWT를 받아오기
        String token = jwtTokenProvider.resolveToken((HttpServletRequest) servletRequest);

        // 유효한 토큰인지 확인
        if(token != null && jwtTokenProvider.validateToken(token)){
            // 토큰이 유효하면 토큰으로부터 유저 정보를 받아오기
            Authentication authentication = jwtTokenProvider.getAuthentication(token);

            // SecurityContext에 Authentication 객체를 저장
            SecurityContextHolder.getContext().setAuthentication(authentication);
            System.out.println("토큰 유효");
        }
        filterChain.doFilter(servletRequest, servletResponse);

    }
}
