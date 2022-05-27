package com.edu.signup.model.dto.login;

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
public class UserSignInReq {

    @NotBlank
    @ApiModelProperty(value = "이메일(가입시 작성한 이메일)", required = true)
    private String userEmail;

    @ApiModelProperty(value = "비밀번호", required = true)
    private String userPassword;

    public static class GetNewAccessToken{
        @ApiModelProperty(value = "Refresh Token Index", required = true)
        private long refreshIdx;
    }

}
