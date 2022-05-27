package com.edu.signup.model.dto.login;

import io.swagger.annotations.ApiModel;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel
public class LoginHistoryRes {

    private String userEmail;
    private String userIp;
    private String userOs;
    private String userPlatform;
    private String loginTime;
    private String loginState;
    private int loginNo;
}
