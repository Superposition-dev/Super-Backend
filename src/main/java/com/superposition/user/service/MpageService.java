package com.superposition.user.service;

import org.springframework.http.ResponseEntity;

public interface MpageService {
    ResponseEntity<?> getUserInfo(String email);

    void getUserLikeProducts(String email);
}
