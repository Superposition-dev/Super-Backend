package com.superposition.user.service;

import com.superposition.user.dto.TokenDTO;

public interface UserService {
    TokenDTO loginByKakao(String code);
}
