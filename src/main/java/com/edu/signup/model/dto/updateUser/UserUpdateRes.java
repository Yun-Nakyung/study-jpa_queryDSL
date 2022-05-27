package com.edu.signup.model.dto.updateUser;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateRes {

    private String userEmail;
    private String userNickName;
    private String userPhone;
    private String userGender;
    private String userBirth;


}
