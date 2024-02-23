package com.superposition.like.service;

import com.superposition.product.dto.ProductListDto;

import java.util.List;

public interface LikeService {
    void likeProduct(long productId, String email);

    void dislikeProduct(long productId, String user);

    List<Long> getLikeProductsByEmail(String email);

    boolean isLike(long productId, String email);
}
