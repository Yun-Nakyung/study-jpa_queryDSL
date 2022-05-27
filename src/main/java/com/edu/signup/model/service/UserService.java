package com.edu.signup.model.service;

import static com.edu.signup.exception.ErrorCode.DUPLICATE_EMAIL;
import static com.edu.signup.exception.ErrorCode.INCONSISTENCY_PASSWORD;
import static com.edu.signup.exception.ErrorCode.INCONSISTENCY_UNLOCK_ACCOUNT;
import static com.edu.signup.exception.ErrorCode.LOCK_ACCOUNT;
import static com.edu.signup.exception.ErrorCode.MEMBER_NOT_FOUND;
import static com.edu.signup.exception.ErrorCode.NOT_FOUND_EMAIL;
import static com.edu.signup.exception.ErrorCode.NOT_FOUND_LOGIN_HISTORY;

import com.edu.signup.config.AesEncryptor;
import com.edu.signup.config.UserInfoMaker;
import com.edu.signup.exception.CustomException;
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
import com.edu.signup.model.mapper.UserMapper;
import com.edu.signup.model.repository.UserRepository;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper uMapper;
    private final UserRepository uRepository;
    private final UserSignUpReq userSignUpReq;
    private final UserInfoMaker userInfoMaker;

    private final AesEncryptor aesEncryptor;

    private final PasswordEncoder passwordEncoder;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    /* 사용자 이메일 검증 */
    public void isDuplicate(String userEmail) throws Exception {
        boolean checkEmail = uMapper.isDuplicate(aesEncryptor.encrypt(userEmail));
        //boolean checkEmail = uRepository.existsByUserEmail(userSignUpReq.toEntity());
        if(checkEmail){
            throw new CustomException(DUPLICATE_EMAIL);
        }
    }

    /* 회원 가입 */
    public int signUpUser(UserSignUpReq user) throws Exception {
        String encodePwd = passwordEncoder.encode(user.getUserPassword());
        user.setUserPassword(encodePwd);

        user.setUserEmail(aesEncryptor.encrypt(user.getUserEmail()));
        user.setUserPhone(aesEncryptor.encrypt(user.getUserPhone()));


        return uMapper.createUser(user);
    }

    /* [관리자] 전체 사용자 정보 조회 */
    public List<AdminFindUserRes> selectAllUsers() throws Exception {

        List<AdminFindUserRes> userList = uMapper.selectAllUsers();
        for (AdminFindUserRes u : userList) {
            u.setUserEmail(aesEncryptor.decrypt(u.getUserEmail()));
            u.setUserPhone(aesEncryptor.decrypt(u.getUserPhone()));
        }
        return userList;
    }

    /* 로그인 서비스 */
    public UserSignInRes signInUser(UserSignInReq loginInfo, HttpServletRequest request) throws Exception {
        UserSignInRes loginUser = uMapper.selectLoginUser(aesEncryptor.encrypt(loginInfo.getUserEmail()));
        if(!loginUser.getUserState().equals("Y")){
            throw new CustomException(MEMBER_NOT_FOUND);
        }
        // 비밀번호 확인
        if(!bCryptPasswordEncoder.matches(loginInfo.getUserPassword(),loginUser.getUserPassword())){
            // 로그인 횟수 증가 및 로그인 이력 추가
            uMapper.increaseLoginCount(loginUser.getUserEmail());
            uMapper.insertLoginHistory(userInfoMaker.getUserInfo(request, "Fail", "비밀번호오류", loginUser.getUserEmail()));
            throw new CustomException(INCONSISTENCY_PASSWORD);
        }

        // 로그인 횟수 초기화 및 로그인 이력 추가
        uMapper.changedLoginCount(loginUser.getUserEmail());
        uMapper.insertLoginHistory(userInfoMaker.getUserInfo(request, "Success", "로그인성공", loginUser.getUserEmail()));

        loginUser.setUserEmail(aesEncryptor.decrypt(loginUser.getUserEmail()));
        loginUser.setUserPhone(aesEncryptor.decrypt(loginUser.getUserPhone()));

        return loginUser;
    }

    /* 사용자 계정 잠금 확인 */
    public void checkLockLoginUser(String userEmail) throws Exception {
        String encryptUserEmail = aesEncryptor.encrypt(userEmail);

        if(uMapper.selectLoginCount(encryptUserEmail) >= 5){
            uMapper.lockLoginUser(encryptUserEmail);
            throw new CustomException(LOCK_ACCOUNT);
        }
    }

    /* 로그인 한 사용자 정보 조회 */
    public UserInfoRes findUserInfo(String userEmail) throws Exception {
        UserInfoRes userInfo = uMapper.findUserInfo(userEmail);
        userInfo.setUserEmail(aesEncryptor.decrypt(userInfo.getUserEmail()));
        userInfo.setUserPhone(aesEncryptor.decrypt(userInfo.getUserPhone()));
        return userInfo;
    }

    /* 사용자 조회 후 비밀번호 확인 */
    public ChangePwdRes checkGuestForChangePwd(ChangePwdReq changePwdReq) throws Exception{

        ChangePwdRes checkedUser = uMapper.checkUserForChangePwd(aesEncryptor.encrypt(
            changePwdReq.getUserEmail()));
        if(checkedUser == null){throw new CustomException(MEMBER_NOT_FOUND);}
        if(!bCryptPasswordEncoder.matches(changePwdReq.getPreviousPassword(),checkedUser.getUserPassword())){
            throw new CustomException(INCONSISTENCY_PASSWORD);
        }

        checkedUser.setUserEmail(aesEncryptor.decrypt(checkedUser.getUserEmail()));

        return checkedUser;
    }

    /* 비밀번호 변경 */
    public int changedUserPassword(ChangePwdRes checkUser) throws Exception {
        checkUser.setUserPassword(bCryptPasswordEncoder.encode(checkUser.getUserPassword()));
        checkUser.setUserEmail(aesEncryptor.encrypt(checkUser.getUserEmail()));
        int changedPassword = uMapper.changedUserPassword(checkUser);
        checkUser.setUserEmail(aesEncryptor.decrypt(checkUser.getUserEmail()));
        return changedPassword;
    }

    /* 로그인 유저 사용자 조회 후 비밀번호 확인 */
    public ChangePwdRes checkUserForChangePwd(ChangePwdAfterLoginReq changeUserPwdReq, String name)
        throws Exception {

        ChangePwdRes checkUser = uMapper.checkUserForChangePwd(name);
        if(!bCryptPasswordEncoder.matches(changeUserPwdReq.getPreviousPassword(), checkUser.getUserPassword())){
            throw new CustomException(INCONSISTENCY_PASSWORD);
        }

        checkUser.setUserEmail(aesEncryptor.decrypt(checkUser.getUserEmail()));
        System.out.println(checkUser.getUserEmail());
        return checkUser;
    }


    /* 로그인 이력 조회 */
    public List<LoginHistoryRes> findLoginHistory(String userEmail) throws Exception {
        List<LoginHistoryRes> loginHistory = uMapper.findLoginHistory(aesEncryptor.encrypt(userEmail));
        if(loginHistory == null){
            throw new CustomException(NOT_FOUND_LOGIN_HISTORY);
        }
        for(LoginHistoryRes u : loginHistory){
            u.setUserEmail(aesEncryptor.decrypt(u.getUserEmail()));
        }
        return loginHistory;
    }

    /* 사용자 계정 잠금 해제 */
    public int clearLoginLock(String userEmail, String releaseString) throws Exception {
        String checkReleaseString = "test";
        if(!releaseString.equals(checkReleaseString)){
            throw new CustomException(INCONSISTENCY_UNLOCK_ACCOUNT);
        }

        String encryptEmail = aesEncryptor.encrypt(userEmail);
        uMapper.clearLoginLock(encryptEmail);
        return uMapper.changedLoginCount(encryptEmail);
    }

    /* 사용자 정보 수정 */
    public int updateUserInfo(UserUpdateReq userUpdateInfo, String name) {
        userUpdateInfo.setUserEmail(name);
        return uMapper.updateUserInfo(userUpdateInfo);
    }

    /* 사용자 회원 탈퇴 */
    public void deleteUser(String userEmail, String userPassword) throws Exception {
        UserSignInRes loginUser = uMapper.selectLoginUser(userEmail);
        if(!bCryptPasswordEncoder.matches(userPassword, loginUser.getUserPassword())){
            throw new CustomException(INCONSISTENCY_PASSWORD);
        }

        uMapper.deleteUser(loginUser.getUserEmail());
    }

    /* [관리자] 이메일을 이용한 사용자 정보 조회 */
    public AdminFindUserRes selectUserInfoByEmail(String userEmail) throws Exception {
        AdminFindUserRes userInfo = uMapper.selectUserInfoByEmail(aesEncryptor.encrypt(userEmail));
        if(userInfo == null){
            throw new CustomException(NOT_FOUND_EMAIL);
        }
        userInfo.setUserEmail(aesEncryptor.decrypt(userInfo.getUserEmail()));
        userInfo.setUserPhone(aesEncryptor.decrypt(userInfo.getUserPhone()));
        return userInfo;
    }


}
