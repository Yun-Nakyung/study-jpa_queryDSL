package com.edu.signup.exception;

import lombok.Getter;

@Getter
//@AllArgsConstructor
public class CustomException extends Exception{

    private ErrorCode errorCode;

    public CustomException(ErrorCode errorCode){
        super(errorCode.getDetail());
        this.errorCode = errorCode;
    }

}
