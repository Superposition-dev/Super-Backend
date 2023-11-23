package com.superposition.product.domain.mapper;

import com.superposition.product.dto.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface ProductMapper {
    public boolean isExistsProduct(@Param("productId") long productId);
    public List<ProductListDto> getAllProducts();
    public List<ProductListDto> getProductsByKeyword(@Param("keyword") String keyword);
    public ProductDto getProductById(@Param("productId") long productId);
    public PictureInfo getPictureInfoById(@Param("productId") long productId);
    public String[] getTagsById(@Param("productId") long productId);
    public Map<String, String> getProductByName(@Param("name") String name);
    public void addBasicView(@Param("productId") long productId);
    public void addQrView(@Param("productId") long productId);
    public void likeProduct(@Param("productId") long productId);
    public void disLikeProduct(@Param("productId") long productId);
    public void instagramClickCount(@Param("productId") long productId);
    public void orderClickCount(@Param("productId") long productId);
    public int getLikeCount(@Param("productId") long productId);
}
