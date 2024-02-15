package com.superposition.user.service;

import com.superposition.user.dto.RequestEditUser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface MpageService {
    ResponseEntity<?> getUserInfo(String email);

    void getUserLikeProducts(String email);

    ResponseEntity<?> editUserProfile(String currentUser, MultipartFile file);

    ResponseEntity<?> editUserInfo(String currentUser, RequestEditUser userInfo);
}
