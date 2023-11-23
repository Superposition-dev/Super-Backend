package com.superposition.product.controller;

import com.superposition.product.dto.Payload;
import com.superposition.product.dto.ResponseProductDetail;
import com.superposition.product.dto.ResponseProduct;
import com.superposition.product.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    @ResponseStatus(HttpStatus.OK)
    public List<ResponseProduct> getAllProducts(@RequestParam(value = "search", defaultValue = " ") String search){
        return productService.getAllProducts(search);
    }

    @GetMapping("/products/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseProductDetail getProductById(@PathVariable String productId, @RequestParam(value = "isQr", defaultValue = "false") boolean isQr){
        return productService.getProductById(productId, isQr);
    }

    @PatchMapping("/products/{productId}/like")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Payload> likeProduct(@PathVariable String productId, @RequestBody Payload isLike){
        return ResponseEntity.ok(productService.likeProduct(productId, isLike.isLike()));
    }

    @PatchMapping("/products/{productId}/google")
    @ResponseStatus(HttpStatus.OK)
    public void orderClickCount(@PathVariable String productId){
        productService.orderClickCount(productId);
    }

    @PatchMapping("/products/{productId}/view")
    @ResponseStatus(HttpStatus.OK)
    public void addView(@PathVariable String productId){
        productService.addView(productId);
    }

}
