package com.superposition.user.service;

import com.superposition.user.dto.TokenDTO;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<?> loginByKakao(String code);
}
