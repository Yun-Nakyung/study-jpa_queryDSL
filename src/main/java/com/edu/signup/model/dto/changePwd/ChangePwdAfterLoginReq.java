package com.edu.signup.model.dto.changePwd;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel
public class ChangePwdAfterLoginReq {

    @ApiModelProperty(position = 2, value = "기존비밀번호", required = true)
    @NotBlank
    private String previousPassword;

    @ApiModelProperty(position = 3, value = "새로운비밀번호", required = true)
    @NotBlank
    private String newPassword;

}
