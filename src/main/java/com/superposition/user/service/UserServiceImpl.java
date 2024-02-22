package com.superposition.user.service;

import com.superposition.user.domain.entity.User;
import com.superposition.user.domain.mapper.UserMapper;
import com.superposition.user.dto.*;
import com.superposition.user.exception.EmptyEmailException;
import com.superposition.user.exception.InvalidTokenException;
import com.superposition.user.jwt.JwtProvider;
import com.superposition.user.jwt.dto.AccessToken;
import com.superposition.user.jwt.dto.JwtToken;
import com.superposition.user.jwt.dto.RefreshToken;
import com.superposition.user.service.login.OAuthLoginService;
import com.superposition.user.service.token.TokenService;
import com.superposition.utils.CookieUtils;
import com.superposition.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.Random;

@Slf4j
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
            boolean activeUser = userMapper.isActiveUser(userInfo.getEmail());
            if(activeUser){
                ResponseUserInfo userInfoByEmail = userMapper.getUserInfoByEmail(userInfo.getEmail());
                JwtToken jwtToken = jwtProvider.generateJwtToken(userInfo.getEmail());

                return ResponseEntity.ok()
                        .header(HttpHeaders.SET_COOKIE, setCookie(jwtToken.getRefreshToken().getRefreshToken()))
                        .body(LoginResponse.builder()
                                .userInfo(userInfoByEmail)
                                .accessToken(jwtToken.getAccessToken())
                                .message("success").build());
            } else {
                Date availableAt = userMapper.getAvailableAt(userInfo.getEmail());
                return ResponseEntity.status(HttpStatus.CONFLICT).body(availableAt);
            }
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
    public ResponseEntity<?> signup(RequestUserInfo requestUserInfo) {
        userMapper.saveUserInfo(toEntity(requestUserInfo));

        ResponseUserInfo userInfo = ResponseUserInfo.builder()
                .email(requestUserInfo.getEmail())
                .nickname(requestUserInfo.getNickname())
                .profile(requestUserInfo.getProfile())
                .isArtist(false).build();

        JwtToken jwtToken = jwtProvider.generateJwtToken(userInfo.getEmail());

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, setCookie(jwtToken.getRefreshToken().getRefreshToken()))
                .body(LoginResponse.builder()
                        .userInfo(userInfo)
                        .accessToken(jwtToken.getAccessToken())
                        .message("success").build());
    }

    @Override
    @Transactional(readOnly = true)
    public boolean checkNickname(String email) {
        if (userMapper.isNullDate(email)) return true;
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
    public ResponseEntity<?> regenerateToken(String rt) {
        if (!StringUtils.hasText(rt)) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Refresh Token is blank");

        try {
            RefreshToken refreshToken = tokenService.getTokenValue(rt, RefreshToken.class);

            JwtToken jwtToken = jwtProvider.generateJwtToken(refreshToken.getEmail());

            return ResponseEntity.ok()
                    .body(AccessToken.builder().accessToken(jwtToken.getAccessToken()).build());
        } catch (InvalidTokenException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Refresh Token is Expired");
        } catch (RuntimeException e){
            log.info(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error");
        }
    }

    private User toEntity(RequestUserInfo userInfo){
        return User.builder()
                .email(userInfo.getEmail())
                .nickname(addIdentifier(userInfo.getNickname()))
                .profile(userInfo.getProfile())
                .gender(userInfo.getGender())
                .birthYear(userInfo.getBirthYear()).build();
    }

    private String addIdentifier(String originNickName){
        return originNickName + getRandomNum();
    }

    private int getRandomNum(){
        Random r = new Random();
        return r.nextInt(8888) + 1111;
    }

    private String setCookie(String refreshToken){
        ResponseCookie cookie = ResponseCookie
                .from(CookieUtils.COOKIE_HEADER_NAME, refreshToken)
                .maxAge(JwtUtils.REFRESH_TOKEN_EXPIRE_TIME)
                .httpOnly(true)
                .secure(true)
                .sameSite(CookieUtils.COOKIE_SAME_SITE)
                .path(CookieUtils.COOKIE_PATH)
                .domain(CookieUtils.DOMAIN)
                .build();

        return cookie.toString();
    }

}
