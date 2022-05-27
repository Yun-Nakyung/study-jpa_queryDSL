package com.edu.signup.model.dto.findUser;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminFindUserRes {

    @ApiModelProperty(value = "이메일", position = 0)
    private String userEmail;

    @ApiModelProperty(value = "사용자 이름", position = 1)
    private String userName;

    @ApiModelProperty(value = "사용자 전화번호", position = 2)
    private String userPhone;

    @ApiModelProperty(value = "사용자 닉네임", position = 3)
    private String userNickName;

    @ApiModelProperty(value = "사용자 성별", position = 4)
    private String userGender;

    @ApiModelProperty(value = "사용자 생년월일", position = 5)
    private String userBirth;

    @ApiModelProperty(value = "사용자 계정 상태", position = 6)
    private boolean userLock;
}
