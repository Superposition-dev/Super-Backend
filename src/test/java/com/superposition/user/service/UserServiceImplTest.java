package com.superposition.user.service;

import com.superposition.user.dto.LoginResponse;
import com.superposition.user.dto.RequestUserInfo;
import com.superposition.user.exception.EmptyEmailException;
import com.superposition.utils.Gender;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserServiceImplTest {
    private final UserService userService;
    private RequestUserInfo userInfo;

    @Autowired
    UserServiceImplTest(UserService userService) {
        this.userService = userService;
    }

    @BeforeEach
    void beforeTest(){
        userInfo = RequestUserInfo.builder().email("test@gamil.com").name("testName").nickname("test").profile("profile").gender(Gender.F).birthDate(new Date(2024,1, 1)).build();
    }

    @Test
    @DisplayName("회원가입 성공 테스트")
    void 회원가입_테스트() {
        //when
        LoginResponse signup = userService.signup(userInfo);

        //then
        assertThat(userInfo.getEmail()).isEqualTo(signup.getUserInfo().getEmail());
    }

    @Test
    @DisplayName("토큰 재발급 실패 테스트")
    void 토큰_재발급(){
        //given
        String email = "no value";

        //when
        ResponseEntity<?> responseEntity = userService.regenerateToken(email);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    @DisplayName("회원 정보 조회")
    void 정보_조회() {
        //given
        String email = userService.signup(userInfo).getUserInfo().getEmail();

        //when
        ResponseEntity<?> info = userService.getUserInfo(email);

        //then
        assertThat(info.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @DisplayName("회원 정보 조회 실패 테스트")
    void 정보_조회2() {
        //given
        String email = null;

        //when
        EmptyEmailException e = assertThrows(EmptyEmailException.class, () -> userService.getUserInfo(email));

        //then
        assertThat(e.getClass()).isEqualTo(EmptyEmailException.class);
    }
}