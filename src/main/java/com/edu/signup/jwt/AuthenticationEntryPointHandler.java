package com.edu.signup.jwt;

import com.edu.signup.exception.ErrorCode;
import com.edu.signup.exception.ErrorResponse;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthenticationEntryPointHandler implements AuthenticationEntryPoint {



    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
        AuthenticationException authException) throws IOException, ServletException {

        String exception = (String) request.getAttribute("exception");
        ErrorCode errorCode;

        // 토큰이 없는 경우 예외처리
        if(exception == null){
            errorCode = ErrorCode.INVALID_AUTH_TOKEN;
            ErrorResponse.toResponseEntity(errorCode);
            return;
        }

        // 토큰이 만료된 경우
        if(exception.equals("ExpiredJwtException")){
            errorCode = ErrorCode.INVALID_REFRESH_TOKEN;
            ErrorResponse.toResponseEntity(errorCode);
            return;
        }
    }
}
