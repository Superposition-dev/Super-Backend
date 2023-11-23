package com.superposition.product.service;

import com.superposition.product.dto.Payload;
import com.superposition.product.dto.ResponseProductDetail;
import com.superposition.product.dto.ResponseProduct;

import java.util.List;

public interface ProductService {
    public List<ResponseProduct> getAllProducts(String search);
    public ResponseProductDetail getProductById(String productId, boolean isQr);
    public Payload likeProduct(String productId, boolean isLike);
    public void orderClickCount(String productId);
    public void addView(String productId);
}
