package com.superposition.user.controller;

import com.superposition.user.dto.RequestUserInfo;
import com.superposition.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping(value = "/signup")
    public ResponseEntity<?> signup(@RequestBody @Valid RequestUserInfo userInfo){
        return userService.signup(userInfo);
    }

    @GetMapping(value = "/login/kakao")
    public ResponseEntity<?> kakaoLogin(@RequestParam String code){
        System.out.println("code = " + code);
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
    public ResponseEntity<?> regenerateToken(@CookieValue("Refresh_Token") String rt) {
        return userService.regenerateToken(rt);
    }
}
