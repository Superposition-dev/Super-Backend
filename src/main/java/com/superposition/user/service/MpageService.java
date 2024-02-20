package com.superposition.user.service;

import com.superposition.product.dto.ProductListDto;
import com.superposition.product.dto.ResponseProduct;
import com.superposition.user.dto.RequestEditUser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MpageService {
    ResponseEntity<?> getUserInfo(String email);

    List<ResponseProduct> getUserLikeProducts(String email);

    String editUserProfile(String currentUser, MultipartFile file);

    void editUserInfo(String currentUser, RequestEditUser userInfo);
}
