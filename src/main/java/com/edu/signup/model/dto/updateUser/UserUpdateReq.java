package com.edu.signup.model.dto.updateUser;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel
public class UserUpdateReq {

    @ApiModelProperty(value = "이메일", hidden = true, example = "aaa@naver.com", position = 1)
    private String userEmail;

    @ApiModelProperty(value = "닉네임", example = "길동길동", position = 2)
    private String userNickName;

    @ApiModelProperty(value = "전화번호", example = "01023456789", position = 3)
    private String userPhone;


}
