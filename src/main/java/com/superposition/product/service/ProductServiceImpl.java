package com.superposition.product.service;

import com.superposition.product.domain.mapper.ProductMapper;
import com.superposition.product.dto.ResponseProduct;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService{
    private ProductMapper productMapper;

    public ProductServiceImpl(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    @Override
    public List<ResponseProduct> getAllProducts() {
        return productMapper.getAllProducts();
    }

    @Override
    public ResponseProduct getProductById(long productId) {
        return productMapper.getProductById(productId);
    }
}
