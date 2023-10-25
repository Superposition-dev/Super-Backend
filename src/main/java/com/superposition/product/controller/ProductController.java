package com.superposition.product.controller;

import com.superposition.product.dto.ResponseProduct;
import com.superposition.product.dto.ResponseProducts;
import com.superposition.product.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<ResponseProducts> getAllProducts(){
        return productService.getAllProducts();
    }

    @GetMapping("/{productId}")
    public ResponseProduct getProductById(@PathVariable long productId, @RequestParam(required = false) boolean isQr){
        return productService.getProductById(productId);
    }

    @PatchMapping("/{productId}/like")
    public void likeProduct(@PathVariable long productId){
        productService.likeProduct(productId);
    }

    @PostMapping("/{productId}/count")
    public void instagramClickCount(@PathVariable long productId){
        productService.instagramClickCount(productId);
    }

    @PostMapping("/{productId}/orderCount")
    public void orderClickCount(@PathVariable long productId){
        productService.orderClickCount(productId);
    }

}
