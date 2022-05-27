package com.edu.signup.model.repository;

import com.edu.signup.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Integer> {

    /* 이메일 중복 확인 */
    boolean existsByUserEmail(String userEmail);

    /* 회원 가입 */


}
