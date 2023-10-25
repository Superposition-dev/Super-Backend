package com.superposition.product.domain.mapper;

import com.superposition.product.dto.ResponseProduct;
import com.superposition.product.dto.ResponseProducts;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductMapper {
    public List<ResponseProducts> getAllProducts();
    public ResponseProduct getProductById(long productId);
    public void likeProduct(long productId);
    public void instagramClickCount(long productId);
    public void orderClickCount(long productId);
    public int getAllView(long productId);
    public String getArtistId(String artistName);
}
