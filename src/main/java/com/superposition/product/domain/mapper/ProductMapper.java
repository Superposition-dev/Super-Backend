package com.superposition.product.domain.mapper;

import com.superposition.product.dto.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface ProductMapper {
    public boolean isExistsProduct(@Param("productId") String productId);
    public List<ProductListDto> getAllProducts();
    public List<ProductListDto> getProductsByKeyword(@Param("keyword") String keyword);
    public ProductDto getProductById(@Param("productId") String productId);
    public PictureInfo getPictureInfoById(@Param("productId") String productId);
    public String[] getTagsById(@Param("productId") String productId);
    public Map<String, String> getProductByName(@Param("name") String name);
    public void addBasicView(@Param("productId") String productId);
    public void addQrView(@Param("productId") String productId);
    public void likeProduct(@Param("productId") String productId);
    public void disLikeProduct(@Param("productId") String productId);
    public void instagramClickCount(@Param("productId") String productId);
    public void orderClickCount(@Param("productId") String productId);
    public int getLikeCount(@Param("productId") String productId);
}
