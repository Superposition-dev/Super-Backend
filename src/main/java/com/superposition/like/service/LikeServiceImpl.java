package com.superposition.like.service;

import com.superposition.like.mapper.LikeMapper;
import com.superposition.like.service.LikeService;
import com.superposition.product.dto.ProductListDto;
import com.superposition.user.exception.ForbiddenException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {
    private final LikeMapper likeMapper;

    @Override
    @Transactional
    public void likeProduct(long productId, String email) {
        likeMapper.likeProductByEmail(productId, email);
    }

    @Override
    @Transactional
    public void dislikeProduct(long productId, String email) {
        if (isLike(productId, email)) {
            likeMapper.disLikeProductByEmail(productId, email);
        } else {
            throw new ForbiddenException();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductListDto> getLikeProductsByEmail(String email) {
        return likeMapper.getLikeProductsByEmail(email);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isLike(long productId, String email) {
        return likeMapper.isLike(productId, email);
    }
}
