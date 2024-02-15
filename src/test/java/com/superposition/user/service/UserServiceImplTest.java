package com.superposition.user.service;

import com.superposition.user.dto.RequestUserInfo;
import com.superposition.user.exception.ForbiddenException;
import com.superposition.utils.Gender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class UserServiceImplTest {
    private final UserService userService;
    private RequestUserInfo userInfo;
    private final MpageService mpageService;

    @Autowired
    UserServiceImplTest(UserService userService, MpageService mpageService) {
        this.userService = userService;
        this.mpageService = mpageService;
    }

    @BeforeEach
    void beforeTest(){
        userInfo = RequestUserInfo.builder().email("test@gamil.com").name("testName").nickname("test").profile("profile").gender(Gender.F).birthYear(2024).build();
    }

    @Test
    @DisplayName("회원가입 성공 테스트")
    void 회원가입_테스트() {
        //when
        HttpStatus statusCode = userService.signup(userInfo).getStatusCode();

        //then
        assertThat(statusCode).isEqualTo(HttpStatus.OK);
    }

    @Test
    @DisplayName("토큰 재발급 실패 테스트")
    void 토큰_재발급(){
        //given
        String rt = null;

        //when
        ResponseEntity<?> responseEntity = userService.regenerateToken(rt);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    @DisplayName("회원 정보 조회")
    void 정보_조회() {
        //given
        userService.signup(userInfo);
        String email = userInfo.getEmail();

        //when
        ResponseEntity<?> info = mpageService.getUserInfo(email);

        //then
        assertThat(info.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @DisplayName("회원 정보 조회 실패 테스트")
    void 정보_조회2() {
        //given
        String email = null;

        //when
        ForbiddenException e = assertThrows(ForbiddenException.class, () -> mpageService.getUserInfo(email));

        //then
        assertThat(e.getClass()).isEqualTo(ForbiddenException.class);
    }

    @Test
    @DisplayName("회원 탈퇴 성공 테스트")
    void 회원_탈퇴() {
        //given
        userService.signup(userInfo);
        String email = userInfo.getEmail();

        //when
        userService.deleteUser(email);

        //then
        assertThrows(ForbiddenException.class, () -> mpageService.getUserInfo(email));
    }
}