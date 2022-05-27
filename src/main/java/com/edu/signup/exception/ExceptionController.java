package com.edu.signup.exception;

import javax.servlet.ServletException;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
//@ControllerAdvice
@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ErrorResponse> methodException(Exception e){
        log.error("Exception : " , e);

        return ErrorResponse.toResponseEntity(ErrorCode.INTERNAL);
    }

    @ExceptionHandler(value = CustomException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(CustomException e){
        log.error("CustomException : ", e);

        return ErrorResponse.toResponseEntity(e.getErrorCode());
    }

    @ExceptionHandler(value = UsernameNotFoundException.class)
    public ResponseEntity<ErrorResponse> usernameNotFoundException(UsernameNotFoundException e){
        log.error("UsernameNotFoundException : ", e);

        return ErrorResponse.toResponseEntity(ErrorCode.NOT_FOUND_EMAIL);
    }

    @ExceptionHandler(value = ServletException.class)
    public ResponseEntity<ErrorResponse> servletException(ServletException e){
        log.error("ServletException : ", e);
        return ErrorResponse.toResponseEntity(ErrorCode.INVALID_ACCOUNT);
    }

    @ExceptionHandler(value = NotFoundException.class)
    public ResponseEntity<ErrorResponse> notFoundException(NotFoundException e){
        log.error("NotFoundException : " + e);
        return ErrorResponse.toResponseEntity(ErrorCode.INTERNAL);
    }


}
