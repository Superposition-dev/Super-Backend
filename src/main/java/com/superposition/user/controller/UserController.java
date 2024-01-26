package com.superposition.user.controller;

import com.superposition.user.dto.LoginResponse;
import com.superposition.user.dto.RequestUserInfo;
import com.superposition.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping(value = "/login/kakao")
    public ResponseEntity<?> kakaoLogin(@RequestParam String code){
        return userService.loginByKakao(code);
    }

    @PostMapping(value = "/signup")
    public ResponseEntity<LoginResponse> signup(@RequestBody @Valid RequestUserInfo userInfo){
        return ResponseEntity.ok(userService.signup(userInfo));
    }

    @GetMapping(value = "/logout")
    public ResponseEntity<Void> logout(@AuthenticationPrincipal String accessToken){
        userService.logout(SecurityContextHolder.getContext().getAuthentication().getName());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/regenerateToken")
    public ResponseEntity<?> regenerateToken(@AuthenticationPrincipal String accessToken){
        return userService.regenerateToken(
                SecurityContextHolder.getContext().getAuthentication().getName());
    }
}
