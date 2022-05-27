package com.edu.signup.model.dto.login;

import io.swagger.annotations.ApiModel;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel
public class LoginHistoryReq {

    private String userEmail;
    private String userIp;
    private String userOs;
    private String userPlatform;
    private String loginTime;
    private String loginState;
    private String loginMessage;
}
