package com.superposition.like.service;

import com.superposition.exception.CommonErrorCode;
import com.superposition.exception.SuperpositionException;
import com.superposition.like.exception.DuplicationException;
import com.superposition.like.mapper.LikeMapper;
import com.superposition.product.domain.mapper.ProductMapper;
import com.superposition.product.exception.NoExistProductException;
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

        if (!isLike(productId, email)){
            likeMapper.likeProductByEmail(productId, email);
        } else {
            throw new DuplicationException();
        }
    }

    @Override
    @Transactional
    public void dislikeProduct(long productId, String email) {
        productCheck(productId);

        if (isLike(productId, email)) {
            likeMapper.dislikeProductByEmail(productId, email);
        } else {
            throw new SuperpositionException(CommonErrorCode.FORBIDDEN);
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

    @Transactional(readOnly = true)
    public void productCheck(long productId){
        if (!productMapper.isExistsProduct(productId)) throw new NoExistProductException("존재하지 않는 게시물입니다.");
    }
}
