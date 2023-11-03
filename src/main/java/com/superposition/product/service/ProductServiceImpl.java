package com.superposition.product.service;

import com.superposition.product.domain.mapper.ProductMapper;
import com.superposition.product.dto.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService{
    private final ProductMapper productMapper;
    private final String instagramUri = "https://www.instagram.com/";

    public ProductServiceImpl(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    @Override
    public List<ResponseProduct> getAllProducts() {
        return toResponseProducts();
    }

    @Override
    public ResponseProductDetail getProductById(long productId, boolean isQr) {
        //조회수 카운트
        addView(productId, isQr);

        //기본 값
        ProductDto productDto = productMapper.getProductById(productId);

        //사진 설명 세팅
        PictureInfo pictureInfo = getPictureInfo(productId);
        //인스타그램 전체 uri 세팅
        String instagramUri = toInstagramUri(productDto.getArtistInstagramId());
        //태그 값 세팅
        String[] tags = getTagsById(productId);


        return ResponseProductDetail.builder().
                        productId(productDto.getProductId()).
                        picture(productDto.getPicture()).
                        title(productDto.getTitle()).
                        tags(tags).
                        artist(productDto.getArtistName()).
                        pictureInfo(pictureInfo).
                        description(productDto.getDescription()).
                        instar(instagramUri).
                        price(productDto.getPrice()).build();
    }

    @Override
    public void likeProduct(long productId, boolean isLike) {
        if (isLike){
            productMapper.likeProduct(productId);
        } else {
            productMapper.disLikeProduct(productId);
        }
    }

    @Override
    public void instagramClickCount(long productId) {
        productMapper.instagramClickCount(productId);
    }

    @Override
    public void orderClickCount(long productId) {
        productMapper.orderClickCount(productId);
    }

    private String toInstagramUri(String artistId){
        return instagramUri + artistId;
    }

    private PictureInfo getPictureInfo(long productId){
        return productMapper.getPictureInfoById(productId);
    }

    private String[] getTagsById(long productId) {
        return productMapper.getTagsById(productId);
    }

    private void addView(long productId, boolean isQr){
        if(isQr){
            productMapper.addBasicView(productId);
        } else {
            productMapper.addQrView(productId);
        }
    }

    private List<ResponseProduct> toResponseProducts(){
        List<ResponseProduct> responseProducts = new ArrayList<>();

        List<ProductListDto> products = productMapper.getAllProducts();

        for (ProductListDto product : products) {
            String[] tags = getTagsById(product.getProductId());

            ResponseProduct responseProduct =
                    ResponseProduct.builder().
                            productId(product.getProductId())
                            .picture(product.getPicture())
                            .tags(tags)
                            .title(product.getTitle())
                            .artist(product.getArtistName())
                            .build();

            responseProducts.add(responseProduct);
        }

        return responseProducts;
    }
}
