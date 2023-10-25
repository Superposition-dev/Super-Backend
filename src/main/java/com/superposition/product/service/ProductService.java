package com.superposition.product.service;

import com.superposition.product.dto.ResponseProduct;

import java.util.List;

public interface ProductService {
    public List<ResponseProduct> getAllProducts();
    public ResponseProduct getProductById(long productId);
}
