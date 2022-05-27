package com.edu.signup.model.dto.signup;

import com.edu.signup.model.entity.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel
public class UserSignUpReq {

    @ApiModelProperty(value = "이름", notes = "홍길동", required = true, position = 0)
    @NotBlank
    private String userName;

    @ApiModelProperty(value = "이메일", example = "aaa@gmail.com",required = true, position = 1)
    @NotBlank @Email
    private String userEmail;

    @ApiModelProperty(value = "전화번호", example = "01023456789",required = true, position = 3)
    @NotBlank //@Pattern(regexp = "(01[016789])(\\d{3,4})(\\d{4})")
    private String userPhone;

    @ApiModelProperty(value = "비밀번호", example = "12345678@",required = true, position = 2)
    @NotBlank
    private String userPassword;


    @ApiModelProperty(value = "별명", position = 6)
    private String userNickName;

    @ApiModelProperty(value = "성별", example = "M / F", position = 5)
    private String userGender;

    @ApiModelProperty(value = "생년월일", example = "20220426", position = 4)
    private String userBirth;

    @ApiModelProperty(value = "권한", example = "관리자 / 사용자", position = 7)
    private String userRole;


    public User toEntity(){
        return User.builder()
            .userEmail(userEmail)
            .userPassword(userPassword)
            .userName(userName)
            .userPhone(userPhone)
            .userNickName(userNickName)
            .userGender(userGender)
            .userBirth(userBirth)
            .userRole(userRole).build();
    }
}
