package com.superposition.user.controller;

import com.superposition.user.dto.LoginResponse;
import com.superposition.user.dto.RequestUserInfo;
import com.superposition.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping(value = "/signup")
    public ResponseEntity<LoginResponse> signup(@RequestBody @Valid RequestUserInfo userInfo){
        return ResponseEntity.ok(userService.signup(userInfo));
    }

    @GetMapping(value = "/login/kakao")
    public ResponseEntity<?> kakaoLogin(@RequestParam String code){
        return userService.loginByKakao(code);
    }

    @GetMapping(value = "/logout")
    public ResponseEntity<Void> logout(@AuthenticationPrincipal UserDetails user){
        userService.logout(user.getUsername());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping
    public void deleteUser(@AuthenticationPrincipal UserDetails user){
        userService.deleteUser(user.getUsername());
    }

    @GetMapping(value = "/isAvailable")
    @ResponseStatus(HttpStatus.OK)
    public Boolean checkUserNickname(@AuthenticationPrincipal UserDetails user){
        return userService.checkNickname(user.getUsername());
    }

    @GetMapping(value = "/regenerateToken")
    public ResponseEntity<?> regenerateToken(@AuthenticationPrincipal UserDetails accessToken) {
        return userService.regenerateToken(
                SecurityContextHolder.getContext().getAuthentication().getName());
    }
}
