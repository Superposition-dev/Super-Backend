package com.superposition.product.controller;

import com.superposition.product.dto.ResponseProduct;
import com.superposition.product.service.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<ResponseProduct> getAllProducts(){
        return productService.getAllProducts();
    }

    @GetMapping("/{postId}")
    public ResponseProduct getProductById(@PathVariable long postId){
        return productService.getProductById(postId);
    }

}
