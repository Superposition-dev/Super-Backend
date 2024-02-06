package com.superposition.product.domain.mapper;

import com.superposition.product.dto.PictureInfo;
import com.superposition.product.dto.ProductDto;
import com.superposition.product.dto.ProductListDto;
import com.superposition.product.dto.SimpleProduct;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ProductMapper {
    public boolean isExistsProduct(@Param("productId") long productId);

    public List<ProductListDto> getAllProducts();

    public List<ProductListDto> getProductsByKeyword(@Param("keyword") String keyword);

    public ProductDto getProductById(@Param("productId") long productId);

    public PictureInfo getPictureInfoById(@Param("productId") long productId);

    public String[] getTagsById(@Param("productId") long productId);

    public List<SimpleProduct> getProductByName(@Param("name") String name);

    public void addBasicView(@Param("productId") long productId);

    public void addQrView(@Param("productId") long productId);

    public void orderClickCount(@Param("productId") long productId);

    public int getLikeCount(@Param("productId") long productId);

    public List<ProductDto> getProductsByExhibitionId(@Param("exhibitionId") long exhibitionId);
}
