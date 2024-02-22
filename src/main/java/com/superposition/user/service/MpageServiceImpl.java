package com.superposition.user.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.superposition.exception.CommonErrorCode;
import com.superposition.exception.SuperpositionException;
import com.superposition.like.service.LikeService;
import com.superposition.product.dto.ResponseProduct;
import com.superposition.product.service.ProductService;
import com.superposition.user.domain.mapper.UserMapper;
import com.superposition.user.dto.RequestEditUser;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DuplicateKeyException;
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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MpageServiceImpl implements MpageService {
    @Value("${naver.objectStorage.bucketName}")
    private String bucketName;

    private final UserMapper userMapper;
    private final LikeService likeService;
    private final AmazonS3 awsS3Client;
    private final ProductService productService;

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<?> getUserInfo(String email) {
        if(StringUtils.hasText(email) && userMapper.isExistUserByEmail(email)){
            return ResponseEntity.ok(userMapper.getUserInfoByEmail(email));
        } else {
            throw new SuperpositionException(CommonErrorCode.FORBIDDEN);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<ResponseProduct> getUserLikeProducts(String email) {
        if(StringUtils.hasText(email)){
            List<Long> likeProductIds = likeService.getLikeProductsByEmail(email);
            return likeProductIds.stream()
                    .map(productService::getProductInfo)
                    .collect(Collectors.toList());
        } else {
            throw new SuperpositionException(CommonErrorCode.FORBIDDEN);
        }
    }

    @Override
    @Transactional
    public String editUserProfile(String currentUser, MultipartFile file) {
        if (userMapper.isExistUserByEmail(currentUser)){
            if (file == null) {
                userMapper.updateUserProfile(currentUser, null);
                return null;
            } else {
                String profile = uploadProfileToStorage(file);
                userMapper.updateUserProfile(currentUser, profile);
                return profile;
            }
        } else {
            throw new SuperpositionException(CommonErrorCode.FORBIDDEN);
        }
    }

    @Override
    @Transactional
    public void editUserInfo(String currentUser, RequestEditUser userInfo) {
        if(currentUser.equals(userInfo.getEmail())){
            try {
                userMapper.updateUserInfo(userInfo);
            } catch (DuplicateKeyException e){
                throw new SuperpositionException(CommonErrorCode.CONFLICT);
            }
        } else {
            throw new SuperpositionException(CommonErrorCode.FORBIDDEN);
        }
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public String uploadProfileToStorage(MultipartFile file) {
        if(file.isEmpty()) {
            return null;
        } else {
            String fileName = file.getOriginalFilename();
            String extension = fileName != null ? fileName.substring(fileName.lastIndexOf(".")) : null;
            String profile = setFileName(extension);

            try {
                byte[] fileData = file.getBytes();
                ObjectMetadata data = new ObjectMetadata();
                data.setContentLength(fileData.length);
                data.setContentDisposition("inline; filename=" + profile);

                awsS3Client.putObject(new PutObjectRequest(
                        bucketName, profile,
                        new ByteArrayInputStream(fileData), data)
                        .withCannedAcl(CannedAccessControlList.PublicRead));
                return profile;
            } catch (IOException e) {
                throw new SuperpositionException(CommonErrorCode.INTERNAL_SERVER_ERROR);
            }
        }
    }

    private String setFileName(String extension){
        if (extension == null) return null;
        return UUID.randomUUID() + extension;
    }
}
