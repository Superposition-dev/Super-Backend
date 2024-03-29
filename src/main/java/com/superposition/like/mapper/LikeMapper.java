package com.superposition.like.mapper;

import com.superposition.product.dto.ProductListDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface LikeMapper {
    void likeProductByEmail(@Param("productId") long productId, @Param("email") String email);

    List<Long> getLikeProductsByEmail(String email);

    boolean isLike(@Param("productId") long productId, @Param("email") String email);

    void dislikeProductByEmail(@Param("productId") long productId, @Param("email") String email);
}
