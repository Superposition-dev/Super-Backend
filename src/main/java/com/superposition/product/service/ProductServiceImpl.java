package com.superposition.product.service;

import com.superposition.product.domain.mapper.ProductMapper;
import com.superposition.product.dto.ResponseProduct;
import com.superposition.product.dto.ResponseProducts;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService{
    private final ProductMapper productMapper;

    public ProductServiceImpl(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    @Override
    public List<ResponseProducts> getAllProducts() {
        return productMapper.getAllProducts();
    }

    @Override
    public ResponseProduct getProductById(long productId) {
        return productMapper.getProductById(productId);
    }

    @Override
    public void likeProduct(long productId) {
        productMapper.likeProduct(productId);
    }

    @Override
    public void instagramClickCount(long productId) {
        productMapper.instagramClickCount(productId);
    }

    @Override
    public void orderClickCount(long productId) {
        productMapper.orderClickCount(productId);
    }

    private int getAllViews(long productId){
        return productMapper.getAllView(productId);
    }

    private String getArtistInstagramId(String artistName){
        return productMapper.getArtistId(artistName);
    }

    private String toInstagramUri(String artistId){
        return "https://www.instagram.com/" + artistId;
    }
}
