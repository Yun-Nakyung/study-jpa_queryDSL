package com.edu.signup.model.mapper;

import com.edu.signup.model.dto.changePwd.ChangePwdRes;
import com.edu.signup.model.dto.findUser.AdminFindUserRes;
import com.edu.signup.model.dto.login.LoginHistoryReq;
import com.edu.signup.model.dto.login.LoginHistoryRes;
import com.edu.signup.model.dto.login.UserSignInRes;
import com.edu.signup.model.dto.signup.UserSignUpReq;
import com.edu.signup.model.dto.updateUser.UserInfoRes;
import com.edu.signup.model.dto.updateUser.UserUpdateReq;
import java.util.ArrayList;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface UserMapper {

    int createUser(UserSignUpReq user);

    boolean isDuplicate(String userEmail);

    List<AdminFindUserRes> selectAllUsers();

    ArrayList<UserSignInRes> findByEmail(String userEmail);

    UserSignInRes selectLoginUser(String userEmail);

    int increaseLoginCount(String userEmail);

    int selectLoginCount(String userEmail);

    void lockLoginUser(String encryptUserEmail);

    int changedUserPassword(ChangePwdRes checkUser);

    int changedLoginCount(String userEmail);

    ChangePwdRes checkUserForChangePwd(String userEmail);

    int insertLoginHistory(LoginHistoryReq loginHistoryReq);

    List<LoginHistoryRes> findLoginHistory(String userEmail);

    int clearLoginLock(String userEmail);

    int updateUserInfo(UserUpdateReq userUpdateInfo);

    UserInfoRes findUserInfo(String userEmail);

    int deleteUser(String userEmail);

    AdminFindUserRes selectUserInfoByEmail(String userEmail);
}
