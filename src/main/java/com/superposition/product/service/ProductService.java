package com.superposition.product.service;

import com.superposition.product.dto.Payload;
import com.superposition.product.dto.ResponseProductDetail;
import com.superposition.product.dto.ResponseProduct;

import java.util.List;
import java.util.Map;

public interface ProductService {
    public List<ResponseProduct> getAllProducts(String search);
    public ResponseProductDetail getProductById(long productId, boolean isQr);
    public List<Map<String, String>> getProductByName(String name);
    public Payload likeProduct(long productId, boolean isLike);
    public void orderClickCount(long productId);
    public void addView(long productId);
}
