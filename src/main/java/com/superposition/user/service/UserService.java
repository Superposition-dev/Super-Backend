package com.superposition.user.service;

import com.superposition.user.dto.LoginResponse;
import com.superposition.user.dto.RequestUserInfo;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<?> loginByKakao(String code);

    void logout(String email);

    LoginResponse signup(RequestUserInfo userInfo);

    ResponseEntity<?> getUserInfo(String email);

    ResponseEntity<?> regenerateToken(String email);
}
