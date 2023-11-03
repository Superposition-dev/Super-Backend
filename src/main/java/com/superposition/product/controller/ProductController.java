package com.superposition.product.controller;

import com.superposition.product.dto.ResponseProductDetail;
import com.superposition.product.dto.ResponseProduct;
import com.superposition.product.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<ResponseProduct> getAllProducts(){
        return productService.getAllProducts();
    }

    @GetMapping("/products/{productId}")
    public ResponseProductDetail getProductById(@PathVariable long productId, @RequestParam(required = false) boolean isQr){
        return productService.getProductById(productId, isQr);
    }

    @PatchMapping("/products/{productId}/like")
    public void likeProduct(@PathVariable long productId, boolean isLike){
        productService.likeProduct(productId, isLike);
    }

    @PatchMapping("/products/{productId}/count")
    public void instagramClickCount(@PathVariable long productId){
        productService.instagramClickCount(productId);
    }

    @PatchMapping("/products/{productId}/orderCount")
    public void orderClickCount(@PathVariable long productId){
        productService.orderClickCount(productId);
    }

    @PatchMapping("/views/{productId}")
    public void view(@PathVariable long productId){

    }

}
