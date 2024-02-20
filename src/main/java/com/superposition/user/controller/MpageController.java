package com.superposition.user.controller;

import com.superposition.user.dto.CurrentUser;
import com.superposition.user.dto.RequestEditUser;
import com.superposition.user.service.MpageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class MpageController {
    private final MpageService mpageService;

    @GetMapping("/me")
    public ResponseEntity<?> getUserInfo(@CurrentUser String email){
        return mpageService.getUserInfo(email);
    }

    @GetMapping("/me/like")
    public ResponseEntity<?> getUserLikeProduct(@CurrentUser String email){
        return ResponseEntity.ok(mpageService.getUserLikeProducts(email));
    }

    @PutMapping ("/edit")
    @ResponseStatus(HttpStatus.OK)
    public void updateUserInfo(@CurrentUser String email, RequestEditUser userInfo){
        mpageService.editUserInfo(email, userInfo);
    }

    @PatchMapping (value = "/edit/profile", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    @ResponseStatus(HttpStatus.OK)
    public String updateUserProfile(@CurrentUser String email, @RequestBody MultipartFile file){
        return mpageService.editUserProfile(email, file);
    }
}
