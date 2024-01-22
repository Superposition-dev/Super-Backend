package com.superposition.user.service;

import com.superposition.user.domain.mapper.UserMapper;
import com.superposition.user.dto.KakaoResponse;
import com.superposition.user.dto.LoginResponse;
import com.superposition.user.dto.TokenDTO;
import com.superposition.user.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final OAuthLoginService oAuthLoginService;
    private final UserMapper userMapper;
    private final JwtProvider jwtProvider;

    @Override
    @Transactional
    public ResponseEntity<?> loginByKakao(String code) {
        String token = oAuthLoginService.getAccessTokenByCode(code);
        KakaoResponse userInfo = oAuthLoginService.getUserInfoByToken(token);

        boolean existUser = userMapper.isExistUserByEmail(userInfo.getEmail());
        if (existUser) { //유저 존재 O 토큰 리턴
            TokenDTO tokenDTO = jwtProvider.generateTokenDto(userInfo.getEmail());
            return ResponseEntity.ok(new LoginResponse(userInfo, tokenDTO));
        } else {
            return ResponseEntity.status(303).body(userInfo);
        }
    }

}
