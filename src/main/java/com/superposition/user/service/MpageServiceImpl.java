package com.superposition.user.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.superposition.like.service.LikeService;
import com.superposition.product.dto.ProductListDto;
import com.superposition.user.domain.mapper.UserMapper;
import com.superposition.user.dto.RequestEditUser;
import com.superposition.user.exception.EmptyEmailException;
import com.superposition.user.exception.ForbiddenException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MpageServiceImpl implements MpageService {
    @Value("${naver.objectStorage.bucketName}")
    private String bucketName;

    private final UserMapper userMapper;
    private final LikeService likeService;
    private final AmazonS3 awsS3Client;

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<?> getUserInfo(String email) {
        if(StringUtils.hasText(email) && userMapper.isExistUserByEmail(email)){
            return ResponseEntity.ok(userMapper.getUserInfoByEmail(email));
        } else {
            throw new ForbiddenException();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductListDto> getUserLikeProducts(String email) {
        if(StringUtils.hasText(email)){
            return likeService.getLikeProductsByEmail(email);
        } else {
            throw new ForbiddenException();
        }
    }

    @Override
    @Transactional
    public void editUserProfile(String currentUser, MultipartFile file) {
        if (userMapper.isExistUserByEmail(currentUser)){
            String profile = uploadProfileToStorage(file);
            userMapper.updateUserProfile(currentUser, profile);
        } else {
            throw new ForbiddenException();
        }
    }

    @Override
    @Transactional
    public void editUserInfo(String currentUser, RequestEditUser userInfo) {
        if(currentUser.equals(userInfo.getEmail())){
            userMapper.updateUserInfo(userInfo);
        } else {
            throw new ForbiddenException();
        }
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public String uploadProfileToStorage(MultipartFile file) {
        String fileName = file.getName();
        String extension = fileName != null ? fileName.substring(fileName.lastIndexOf(".")) : null;
        String profile = setFileName(extension);

        try {
            byte[] fileData = file.getBytes();
            ObjectMetadata data = new ObjectMetadata();
            data.setContentLength(fileData.length);

            file.getName().lastIndexOf(".");

            awsS3Client.putObject(new PutObjectRequest(
                    bucketName, profile,
                    new ByteArrayInputStream(fileData), data));

            return profile;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String setFileName(String extension){
        if (extension == null) return null;
        return UUID.randomUUID() + extension;
    }
}
