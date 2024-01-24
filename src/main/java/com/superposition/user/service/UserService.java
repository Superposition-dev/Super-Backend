package com.superposition.user.service;

import com.superposition.user.dto.LoginResponse;
import com.superposition.user.dto.RequestUserInfo;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<?> loginByKakao(String code);

    LoginResponse signup(RequestUserInfo userInfo);
}
