package com.superposition.product.service;

import com.superposition.product.domain.mapper.ProductMapper;
import com.superposition.product.dto.ResponseProductDetail;
import com.superposition.product.dto.ResponseProduct;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class ProductServiceImplTest {
    private final ProductService productService;
    private final ProductMapper productMapper;

    @Autowired
    public ProductServiceImplTest(ProductService productService, ProductMapper productMapper, ProductMapper productMapper1) {
        this.productService = productService;
        this.productMapper = productMapper1;
    }

    @Test
    void 게시물_전체_조회_테스트() {
        //when
        List<ResponseProduct> allProducts = productService.getAllProducts();

        //then
        assertThat(allProducts.size()).isEqualTo(4);
    }

    @Test
    void 게시물_개별_조회_테스트() {
        //given
        long postId = 1;

        //when
        ResponseProductDetail responseProductDetail = productService.getProductById(postId, false);

        //then
        assertThat(responseProductDetail).isNotNull();
    }

    @Test
    void 좋아요_성공_테스트() {
        //given
        long productId = 1;

        //when
        productService.likeProduct(productId, true);

        //then
        int likeCount = productMapper.getLikeCount(productId);
        assertThat(likeCount).isEqualTo(1);
    }

    @Test
    void 좋아요_취소_성공_테스트() {
        //given
        long productId = 1;
        productService.likeProduct(productId, true);

        //when
        productService.likeProduct(productId, false);

        //then
        int likeCount = productMapper.getLikeCount(productId);
        assertThat(likeCount).isEqualTo(0);
    }

}