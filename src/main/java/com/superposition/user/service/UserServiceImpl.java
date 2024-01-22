package com.superposition.user.service;

import com.superposition.user.domain.mapper.UserMapper;
import com.superposition.user.dto.KakaoResponse;
import com.superposition.user.dto.TokenDTO;
import com.superposition.user.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
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
    public TokenDTO loginByKakao(String code) {
        String token = oAuthLoginService.getAccessTokenByCode(code);
        KakaoResponse userInfo = oAuthLoginService.getUserInfoByToken(token);

        boolean existUser = userMapper.isExistUserByEmail(userInfo.getEmail());
        if (existUser) { //유저 존재 O 토큰 리턴
            TokenDTO tokenDTO = jwtProvider.generateTokenDto(userInfo.getEmail());
            System.out.println("tokenDTO = " + tokenDTO);
            return tokenDTO;
        } else {
            throw new RuntimeException("300에러");
        }
    }

    private void signUp(KakaoResponse user){
        System.out.println("회원가입 실행");
    }
}
