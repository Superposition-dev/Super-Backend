package com.superposition.product.service;

import com.superposition.product.dto.ResponseProductDetail;
import com.superposition.product.dto.ResponseProduct;

import java.util.List;

public interface ProductService {
    public List<ResponseProduct> getAllProducts();
    public ResponseProductDetail getProductById(long productId, boolean isQr);
    public void likeProduct(long productId, boolean isLike);
    public void instagramClickCount(long productId);
    public void orderClickCount(long productId);
}
