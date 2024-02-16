package com.superposition.user.controller;

import com.superposition.user.dto.RequestEditUser;
import com.superposition.user.service.MpageService;
import com.superposition.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
    public ResponseEntity<?> getUserLikeProduct(@AuthenticationPrincipal UserDetails user){
        return ResponseEntity.ok(mpageService.getUserLikeProducts(user.getUsername()));
    }

    @PutMapping ("/edit")
    @ResponseStatus(HttpStatus.OK)
    public void updateUserInfo(@AuthenticationPrincipal UserDetails user, RequestEditUser userInfo){
        mpageService.editUserInfo(user.getUsername(), userInfo);
    }

    @PatchMapping (value = "/edit/profile", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    @ResponseStatus(HttpStatus.OK)
    public String updateUserProfile(@AuthenticationPrincipal UserDetails user, @RequestBody MultipartFile file){
        return mpageService.editUserProfile(user.getUsername(), file);
    }
}
