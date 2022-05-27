package com.edu.signup.model.dto.changePwd;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangePwdRes {

    private String userEmail;
    private String userPassword;
    private String userRole;
}
