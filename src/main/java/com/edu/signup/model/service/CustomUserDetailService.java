package com.edu.signup.model.service;

import com.edu.signup.config.AesEncryptor;
import com.edu.signup.exception.ErrorCode;
import com.edu.signup.model.dto.login.CustomUserDetails;
import com.edu.signup.model.dto.login.UserSignInRes;
import com.edu.signup.model.mapper.UserMapper;
import java.util.ArrayList;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomUserDetailService implements UserDetailsService {

    private final UserMapper uMapper;

    private final AesEncryptor aesEncryptor;

    @SneakyThrows
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String encryptUserEmail = aesEncryptor.encrypt(username);

        ArrayList<UserSignInRes> user = uMapper.findByEmail(encryptUserEmail);
        if(uMapper.findByEmail(encryptUserEmail) == null){
            throw new UsernameNotFoundException(ErrorCode.NOT_FOUND_EMAIL.getDetail());
        }

        return new CustomUserDetails(user);
    }

}
