package com.superposition.product.service;

import com.superposition.product.dto.ResponseProduct;
import com.superposition.product.dto.ResponseProducts;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

public interface ProductService {
    public List<ResponseProducts> getAllProducts();
    public ResponseProduct getProductById(long productId);
    public void likeProduct(long productId);
    public void instagramClickCount(long productId);
    public void orderClickCount(long productId);
}
