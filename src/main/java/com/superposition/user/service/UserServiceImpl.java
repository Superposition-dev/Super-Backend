package com.superposition.user.service;

import com.superposition.user.domain.entity.User;
import com.superposition.user.domain.mapper.UserMapper;
import com.superposition.user.dto.UserInfo;
import com.superposition.user.dto.LoginResponse;
import com.superposition.user.dto.RequestUserInfo;
import com.superposition.user.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<Object> loginByKakao(String code) {
        String token = oAuthLoginService.getAccessTokenByCode(code);
        UserInfo userInfo = oAuthLoginService.getUserInfoByToken(token);

        boolean existUser = userMapper.isExistUserByEmail(userInfo.getEmail());
        if (existUser) { //유저 존재 O 토큰 리턴
            return ResponseEntity.ok(LoginResponse.builder()
                    .userInfo(userInfo)
                    .accessToken(getAccessToken(userInfo.getEmail()))
                    .build());
        } else {
            return ResponseEntity.status(HttpStatus.SEE_OTHER).body(userInfo);
        }
    }

    @Override
    @Transactional
    public LoginResponse signup(RequestUserInfo requestUserInfo) {
        userMapper.saveUserInfo(toEntity(requestUserInfo));

        UserInfo userInfo = UserInfo.builder()
                .email(requestUserInfo.getEmail())
                .nickname(requestUserInfo.getNickname())
                .profileImage(requestUserInfo.getProfile())
                .build();

        return LoginResponse.builder()
                .userInfo(userInfo)
                .accessToken(getAccessToken(userInfo.getEmail()))
                .build();
    }

    private User toEntity(RequestUserInfo userInfo){
        return User.builder()
                .email(userInfo.getEmail())
                .nickname(userInfo.getNickname())
                .profile(userInfo.getProfile())
                .gender(userInfo.getGender())
                .birthDate(userInfo.getBirthDate()).build();
    }

    private String getAccessToken(String email){
        return jwtProvider.generateTokenDto(email).getAccessToken();
    }

}
