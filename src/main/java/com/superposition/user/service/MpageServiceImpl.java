package com.superposition.user.service;

import com.superposition.like.service.LikeService;
import com.superposition.user.domain.mapper.UserMapper;
import com.superposition.user.exception.EmptyEmailException;
import com.superposition.user.exception.ForbiddenException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class MpageServiceImpl implements MpageService {
    private final UserMapper userMapper;
    private final LikeService likeService;

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
    public void getUserLikeProducts(String email) {
        if(StringUtils.hasText(email)){
            likeService.getLikeProductsByEmail(email);
        } else {
            throw new EmptyEmailException();
        }
    }
}
