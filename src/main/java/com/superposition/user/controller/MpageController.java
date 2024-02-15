package com.superposition.user.controller;

import com.superposition.user.dto.RequestEditUser;
import com.superposition.user.service.MpageService;
import com.superposition.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class MpageController {
    private final MpageService mpageService;

    //마이페이지
    @GetMapping("/me")
    public ResponseEntity<?> getUserInfo(@AuthenticationPrincipal UserDetails user){
        return mpageService.getUserInfo(user.getUsername());
    }

    @GetMapping("/me/like")
    public void getUserLikeProduct(@AuthenticationPrincipal UserDetails user){
        mpageService.getUserLikeProducts(user.getUsername());
    }

    @PutMapping ("/edit")
    public void updateUserInfo(UserDetails user, RequestEditUser userInfo){
        mpageService.editUserInfo(user.getUsername(), userInfo);
    }

    @PatchMapping ("/edit/profile")
    public void updateUserProfile(@AuthenticationPrincipal UserDetails user, MultipartFile file){
        mpageService.editUserProfile(user.getUsername(), file);
    }
}
