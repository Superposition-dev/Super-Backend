package com.superposition.product.service;

import com.superposition.product.dto.*;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Map;

public interface ProductService {
    public List<ResponseProduct> getAllProducts(String search);
    public ResponseProductDetail getProductById(long productId, boolean isQr, UserDetails user);
    public List<SimpleProduct> getProductByName(String name);
    public void likeProduct(long productId, UserDetails email);

    void dislikeProduct(long productId, UserDetails user);

    public void orderClickCount(long productId);
    public void addView(long productId);
    public ResponseProduct getProductInfo(long productId);
}
