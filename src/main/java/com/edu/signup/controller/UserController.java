package com.edu.signup.controller;

import static com.edu.signup.exception.ErrorCode.INTERNAL;

import com.edu.signup.exception.CustomException;
import com.edu.signup.jwt.JwtTokenProvider;
import com.edu.signup.model.dto.changePwd.ChangePwdAfterLoginReq;
import com.edu.signup.model.dto.changePwd.ChangePwdReq;
import com.edu.signup.model.dto.changePwd.ChangePwdRes;
import com.edu.signup.model.dto.findUser.AdminFindUserRes;
import com.edu.signup.model.dto.login.LoginHistoryRes;
import com.edu.signup.model.dto.login.UserSignInReq;
import com.edu.signup.model.dto.login.UserSignInRes;
import com.edu.signup.model.dto.signup.UserSignUpReq;
import com.edu.signup.model.dto.updateUser.UserInfoRes;
import com.edu.signup.model.dto.updateUser.UserUpdateReq;
import com.edu.signup.model.service.CustomUserDetailService;
import com.edu.signup.model.service.UserService;
import io.swagger.annotations.ApiOperation;
import java.security.Principal;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService uService;

    private final JwtTokenProvider jwtTokenProvider;
    private final CustomUserDetailService userDetailService;

    @ApiOperation(value = "사용자 회원가입")
    @PostMapping("/account")
    public int createUser(@Valid @RequestBody UserSignUpReq user) throws Exception {
        uService.isDuplicate(user.getUserEmail());

        int createUser = uService.signUpUser(user);

        return createUser;
    }

    @ApiOperation(value = "로그인")
    @PostMapping("/account/login")
    public String loginUser(@RequestBody UserSignInReq loginInfo, HttpServletRequest request) throws Exception {

        userDetailService.loadUserByUsername(loginInfo.getUserEmail());
        uService.checkLockLoginUser(loginInfo.getUserEmail());
        UserSignInRes loginUser = uService.signInUser(loginInfo, request);

        return jwtTokenProvider.createToken(loginUser);
    }

    @ApiOperation(value = "비밀번호 변경")
    @PatchMapping("/account/password")
    public void changedGuestPassword(@Valid @RequestBody ChangePwdReq changePwdReq)
        throws Exception {

        ChangePwdRes checkUser = uService.checkGuestForChangePwd(changePwdReq);

        checkUser.setUserPassword(changePwdReq.getNewPassword());
        if(uService.changedUserPassword(checkUser) != 1) {
            throw new CustomException(INTERNAL);
        }

    }

    @ApiOperation(value = "잠금 해제")
    @PostMapping("/account/unlock/{userEmail}/{releaseString}")
    public void unlockAccount(@PathVariable String userEmail, @PathVariable String releaseString) throws Exception {
        userDetailService.loadUserByUsername(userEmail);
        uService.clearLoginLock(userEmail, releaseString);
    }

    @ApiOperation(value = "로그인 사용자 비밀번호 변경")
    @PatchMapping("/user/password")
    public String changedUserPassword(@RequestBody ChangePwdAfterLoginReq changeUserPwdReq, Principal principal)
        throws Exception {
        ChangePwdRes checkUser = uService.checkUserForChangePwd(changeUserPwdReq, principal.getName());
        checkUser.setUserPassword(changeUserPwdReq.getNewPassword());
        if(uService.changedUserPassword(checkUser) != 1){
            throw new CustomException(INTERNAL);
        }

        UserSignInRes loginUser = new UserSignInRes();
        loginUser.setUserEmail(checkUser.getUserEmail());
        loginUser.setUserRole(checkUser.getUserRole());
        return jwtTokenProvider.createToken(loginUser);
    }

    @ApiOperation(value = "사용자 정보 조회", notes = "사용자의 Email을 통해 사용자 정보를 조회")
    @GetMapping("/user")
    public UserInfoRes findUserInfo(Principal principal) throws Exception {
        return uService.findUserInfo(principal.getName());
    }

    @ApiOperation(value = "회원 정보 수정")
    @PatchMapping("/user")
    public void updateUserInfo(@RequestBody UserUpdateReq userUpdateInfo, Principal principal) {

        uService.updateUserInfo(userUpdateInfo, principal.getName());
    }

    @ApiOperation(value = "회원 탈퇴")
    @DeleteMapping("/user/{userPassword}")
    public void deleteUser(Principal principal, @PathVariable String userPassword)
        throws Exception {
        uService.deleteUser(principal.getName(), userPassword);
    }

    @ApiOperation(value = "전체 사용자 정보 조회")
    @GetMapping("/admin/user")
    public List<AdminFindUserRes> findUserListAdmin() throws Exception {
        return uService.selectAllUsers();
    }

    @ApiOperation(value = "로그인 이력 조회")
    @GetMapping("/admin/history/{userEmail}")
    public List<LoginHistoryRes> selectLoginHistory(@PathVariable String userEmail) throws Exception {
        return uService.findLoginHistory(userEmail);
    }

    @ApiOperation(value = "이메일을 이용한 사용자 정보 조회")
    @GetMapping("/admin/user/{userEmail}")
    public AdminFindUserRes findUserInfoAdmin(@PathVariable String userEmail) throws Exception {
        return uService.selectUserInfoByEmail(userEmail);

    }

    public void test(){

    }


}
