package com.superposition.product.domain.mapper;

import com.superposition.product.dto.ResponseProduct;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductMapper {
    public List<ResponseProduct> getAllProducts();
    public ResponseProduct getProductById(long productId);
}
