package com.superposition.product.controller;

import com.superposition.product.dto.ResponseProductDetail;
import com.superposition.product.dto.ResponseProduct;
import com.superposition.product.service.ProductService;
import com.superposition.user.dto.CurrentUser;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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
    public ResponseProductDetail getProductById(@PathVariable long productId, @RequestParam(value = "isQr", defaultValue = "false") boolean isQr, @AuthenticationPrincipal UserDetails user){
        return productService.getProductById(productId, isQr, user);
    }

    @PostMapping("/products/{productId}/like")
    @ResponseStatus(HttpStatus.OK)
    public void likeProduct(@PathVariable long productId, @CurrentUser String email){
        productService.likeProduct(productId, email);
    }

    @DeleteMapping ("/products/{productId}/dislike")
    @ResponseStatus(HttpStatus.OK)
    public void dislikeProduct(@PathVariable long productId, @CurrentUser String email){
        productService.dislikeProduct(productId, email);
    }

    @PatchMapping("/products/{productId}/google")
    @ResponseStatus(HttpStatus.OK)
    public void orderClickCount(@PathVariable long productId){
        productService.orderClickCount(productId);
    }

    @PatchMapping("/products/{productId}/view")
    @ResponseStatus(HttpStatus.OK)
    public void addView(@PathVariable long productId){
        productService.addView(productId);
    }

}
