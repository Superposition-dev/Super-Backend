package com.superposition.product.service;

import com.superposition.product.dto.Payload;
import com.superposition.product.dto.ResponseProductDetail;
import com.superposition.product.dto.ResponseProduct;

import java.util.List;

public interface ProductService {
    public List<ResponseProduct> getAllProducts(String filter);
    public ResponseProductDetail getProductById(long productId, boolean isQr);
    public Payload likeProduct(long productId, boolean isLike);
    public void instagramClickCount(long productId);
    public void orderClickCount(long productId);
}
