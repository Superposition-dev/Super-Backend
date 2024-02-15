package com.superposition.like.service;

import com.superposition.like.mapper.LikeMapper;
import com.superposition.product.domain.mapper.ProductMapper;
import com.superposition.product.dto.ProductListDto;
import com.superposition.product.exception.NoExistProductException;
import com.superposition.product.service.ProductService;
import com.superposition.user.exception.ForbiddenException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {
    private final LikeMapper likeMapper;
    private final ProductMapper productMapper;

    @Override
    @Transactional
    public void likeProduct(long productId, String email) {
        productCheck(productId);

        likeMapper.likeProductByEmail(productId, email);
    }

    @Override
    @Transactional
    public void dislikeProduct(long productId, String email) {
        productCheck(productId);

        if (isLike(productId, email)) {
            likeMapper.dislikeProductByEmail(productId, email);
        } else {
            throw new ForbiddenException();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Long> getLikeProductsByEmail(String email) {
        return likeMapper.getLikeProductsByEmail(email);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isLike(long productId, String email) {
        return likeMapper.isLike(productId, email);
    }

    private void productCheck(long productId){
        if (!productMapper.isExistsProduct(productId)) throw new NoExistProductException("존재하지 않는 게시물입니다.");
    }
}
