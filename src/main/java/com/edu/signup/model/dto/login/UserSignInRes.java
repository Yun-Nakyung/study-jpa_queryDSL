package com.edu.signup.model.dto.login;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSignInRes {

    @ApiModelProperty(value = "이름", notes = "홍길동", required = true, position = 1)
    private String userName;

    @JsonIgnore
    @ApiModelProperty(value = "비밀번호", hidden = true)
    private String userPassword;

    @ApiModelProperty(value = "이메일", example = "aaa@gmail.com",required = true, position = 2)
    private String userEmail;

    @ApiModelProperty(value = "전화번호", example = "01023456789",required = true, position = 4)
    private String userPhone;

    @ApiModelProperty(value = "별명", position = 7)
    private String userNickName;

    @ApiModelProperty(value = "성별", example = "M / F", position = 6)
    private String userGender;

    @ApiModelProperty(value = "생년월일", example = "20220426", position = 5)
    private String userBirth;

    @ApiModelProperty(hidden = true)
    private String userRole;

    @ApiModelProperty(hidden = true)
    private String userState;

    @ApiModelProperty(hidden = true)
    private boolean userLock;
}
