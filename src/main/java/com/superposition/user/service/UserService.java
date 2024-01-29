package com.superposition.user.service;

import com.superposition.user.dto.LoginResponse;
import com.superposition.user.dto.RequestUserInfo;
import org.springframework.http.ResponseEntity;

public interface UserService {
    LoginResponse signup(RequestUserInfo userInfo);

    ResponseEntity<?> loginByKakao(String code);

    void logout(String email);

    ResponseEntity<?> getUserInfo(String email);

    boolean checkNickname(String email);

    ResponseEntity<?> regenerateToken(String email);
}