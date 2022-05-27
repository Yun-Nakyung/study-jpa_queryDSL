package com.edu.signup.model.dto.updateUser;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoRes {

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
}
