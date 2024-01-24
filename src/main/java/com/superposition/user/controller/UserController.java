package com.superposition.user.controller;

import com.superposition.user.dto.LoginResponse;
import com.superposition.user.dto.RequestUserInfo;
import com.superposition.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping(value = "/login/kakao")
    public ResponseEntity<?> kakaoLogin(@RequestParam String code){
        return ResponseEntity.ok(userService.loginByKakao(code));
    }

    @PostMapping(value = "/signup")
    public ResponseEntity<LoginResponse> signup(@RequestBody RequestUserInfo userInfo){
        return ResponseEntity.ok(userService.signup(userInfo));
    }
}
