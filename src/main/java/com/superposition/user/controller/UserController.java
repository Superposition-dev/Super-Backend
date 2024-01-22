package com.superposition.user.controller;

import com.superposition.user.dto.TokenDTO;
import com.superposition.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping(value = "/login/kakao")
    public ResponseEntity<?> kakaoLogin(@RequestParam String code){
        return userService.loginByKakao(code);
    }
}
