package com.edu.signup.config;

import com.edu.signup.model.dto.login.LoginHistoryReq;
import com.edu.signup.model.dto.login.UserSignInRes;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

//@Builder
@Component
@Getter
@RequiredArgsConstructor
public class UserInfoMaker {

    //private final LoginHistoryReq loginHistoryReq;

    public LoginHistoryReq getUserInfo(HttpServletRequest request, String loginState, String loginMessage, String userEmail){

        String userIp = "";
        String userOs = "";
        String userPlatform = "";
        String userAgent = request.getHeader("User-Agent").toLowerCase();

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        String loginTime = dateFormat.format(date);
        System.out.println(loginTime);

        // IP
        userIp = request.getHeader("X-Forwarded-For");
        if(userIp == null){
            userIp = request.getRemoteAddr();
        }

        // Platform
        if(userAgent.indexOf("trident") > -1){
            userPlatform = "ie";
        } else if (userAgent.indexOf("edge") > -1) {
            userPlatform = "edge";
        } else if (userAgent.indexOf("whale") > -1) {
            userPlatform = "Whale";
        } else if (userAgent.indexOf("firefox") > -1) {
            userPlatform = "firefox";
        } else if (userAgent.indexOf("chrome") > -1) {
            userPlatform = "chrome";
        }

        // OS
        if (userAgent.indexOf("windows nt 10.0") > -1) {
            userOs = "Windows10";
        }else if (userAgent.indexOf("windows nt 6.1") > -1) {
            userOs = "Windows7";
        }else if (userAgent.indexOf("windows nt 6.2") > -1 || userAgent.indexOf("windows nt 6.3") > -1 ) {
            userOs = "Windows8";
        }else if (userAgent.indexOf("windows nt 6.0") > -1) {
            userOs = "WindowsVista";
        }else if (userAgent.indexOf("windows nt 5.1") > -1) {
            userOs = "WindowsXP";
        }else if (userAgent.indexOf("windows nt 5.0") > -1) {
            userOs = "Windows2000";
        }else if (userAgent.indexOf("windows nt 4.0") > -1) {
            userOs = "WindowsNT";
        }else if (userAgent.indexOf("windows 98") > -1) {
            userOs = "Windows98";
        }else if (userAgent.indexOf("windows 95") > -1) {
            userOs = "Windows95";
        }else if (userAgent.indexOf("iphone") > -1) {
            userOs = "iPhone";
        }else if (userAgent.indexOf("ipad") > -1) {
            userOs = "iPad";
        }else if (userAgent.indexOf("android") > -1) {
            userOs = "android";
        }else if (userAgent.indexOf("mac") > -1) {
            userOs = "mac";
        }else if (userAgent.indexOf("linux") > -1) {
            userOs = "Linux";
        }else{
            userOs = "Other";
        }

        LoginHistoryReq loginHistoryReq = new LoginHistoryReq();

        loginHistoryReq.setUserIp(userIp);
        loginHistoryReq.setUserOs(userOs);
        loginHistoryReq.setUserPlatform(userPlatform);
        loginHistoryReq.setLoginTime(loginTime);
        loginHistoryReq.setLoginState(loginState);
        loginHistoryReq.setLoginMessage(loginMessage);
        loginHistoryReq.setUserEmail(userEmail);

        return loginHistoryReq;
    }
}
