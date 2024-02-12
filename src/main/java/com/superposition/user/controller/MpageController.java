package com.superposition.user.controller;

import com.superposition.user.service.MpageService;
import com.superposition.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users/me")
@RequiredArgsConstructor
public class MpageController {
    private final MpageService mpageService;

    //마이페이지
    @GetMapping
    public ResponseEntity<?> getUserInfo(@AuthenticationPrincipal UserDetails user){
        return mpageService.getUserInfo(user.getUsername());
    }

    @GetMapping("/like")
    public void getUserLikeProduct(@AuthenticationPrincipal UserDetails user){
        mpageService.getUserLikeProducts(user.getUsername());
    }

}
