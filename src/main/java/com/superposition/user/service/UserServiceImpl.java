package com.superposition.user.service;

import com.superposition.user.domain.entity.User;
import com.superposition.user.domain.mapper.UserMapper;
import com.superposition.user.dto.*;
import com.superposition.user.exception.EmptyEmailException;
import com.superposition.user.exception.InvalidTokenException;
import com.superposition.user.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final OAuthLoginService oAuthLoginService;
    private final TokenService tokenService;
    private final UserMapper userMapper;
    private final JwtProvider jwtProvider;

    @Override
    @Transactional
    public ResponseEntity<?> loginByKakao(String code) {
        String token = oAuthLoginService.getAccessTokenByCode(code);
        UserInfo userInfo = oAuthLoginService.getUserInfoByToken(token);

        boolean existUser = userMapper.isExistUserByEmail(userInfo.getEmail());
        if (existUser) {
            ResponseUserInfo userInfoByEmail = userMapper.getUserInfoByEmail(userInfo.getEmail());
            return ResponseEntity.ok(LoginResponse.builder()
                    .userInfo(userInfoByEmail)
                    .accessToken(jwtProvider.generateJwtToken(userInfo.getEmail()).getAccessToken())
                    .build());
        } else {
            return ResponseEntity.status(HttpStatus.SEE_OTHER).body(userInfo);
        }
    }

    @Override
    public void logout(String email) {
        if(StringUtils.hasText(email)){
            tokenService.deleteTokenValue(email);
        } else {
            throw new EmptyEmailException();
        }
    }

    @Override
    @Transactional
    public LoginResponse signup(RequestUserInfo requestUserInfo) {
        userMapper.saveUserInfo(toEntity(requestUserInfo));

        ResponseUserInfo userInfo = ResponseUserInfo.builder()
                .email(requestUserInfo.getEmail())
                .name(requestUserInfo.getName())
                .nickname(requestUserInfo.getNickname())
                .profile(requestUserInfo.getProfile())
                .gender(requestUserInfo.getGender())
                .birthDate(requestUserInfo.getBirthDate()).build();

        return LoginResponse.builder()
                .userInfo(userInfo)
                .accessToken(jwtProvider.generateJwtToken(userInfo.getEmail()).getAccessToken())
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<?> getUserInfo(String email) {
        if(StringUtils.hasText(email)){
            return ResponseEntity.ok(userMapper.getUserInfoByEmail(email));
        } else {
            throw new EmptyEmailException();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public boolean checkNickname(String email) {
        return userMapper.isAvailableChange(email);
    }

    @Override
    @Transactional
    public void deleteUser(String email) {
        if(StringUtils.hasText(email)){
            userMapper.deleteUserByEmail(email);
        } else {
            throw new EmptyEmailException();
        }
    }

    @Override
    public ResponseEntity<?> regenerateToken(String email) {
        if(!StringUtils.hasText(email)) throw new EmptyEmailException();

        try {
            RefreshToken refreshToken = tokenService.getTokenValue(
                    email, RefreshToken.class);
            return ResponseEntity.ok(jwtProvider.generateJwtToken(refreshToken.getEmail()).getAccessToken());
        } catch (InvalidTokenException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Refresh Token is Expired");
        }
    }

    private User toEntity(RequestUserInfo userInfo){
        return User.builder()
                .email(userInfo.getEmail())
                .nickname(addIdentifier(userInfo.getNickname()))
                .profile(userInfo.getProfile())
                .gender(userInfo.getGender())
                .birthDate(userInfo.getBirthDate()).build();
    }

    private String addIdentifier(String originNickName){
        return originNickName + getRandomNum();
    }

    private int getRandomNum(){
        Random r = new Random();
        return r.nextInt(8888) + 1111;
    }

}
