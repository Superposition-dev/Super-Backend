package com.superposition.product.service;

import com.superposition.product.domain.mapper.ProductMapper;
import com.superposition.product.dto.*;
import com.superposition.product.exception.NoExistProductException;
import com.superposition.product.exception.NoSearchException;
import com.superposition.product.utils.Path;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService{
    private final ProductMapper productMapper;

    public ProductServiceImpl(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    @Override
    public List<ResponseProduct> getAllProducts(String filter) {
        if(!filter.trim().isBlank()){
            return toResponseProducts(searchByKeyword(filter));
        } else {
            return toResponseProducts(productMapper.getAllProducts());
        }
    }

    @Override
    public ResponseProductDetail getProductById(long productId, boolean isQr) {
        return toReponseBuild(productId, isQr);
    }

    @Override
    public Payload likeProduct(long productId, boolean isLike) {
        if (isLike){
            productMapper.likeProduct(productId);
            return Payload.builder().like(true).build();
        } else {
            productMapper.disLikeProduct(productId);
            return Payload.builder().like(false).build();
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

    private String addView(long productId, boolean isQr){
        if(isQr){
            productMapper.addQrView(productId);
            return "QR 코드가 인정되었습니다.";
        } else {
            productMapper.addBasicView(productId);
            return "일반 조회입니다.";
        }
    }

    private List<ProductListDto> searchByKeyword(String keyword){
        return productMapper.getProductsByKeyword(keyword);
    }

    private List<ResponseProduct> toResponseProducts(List<ProductListDto> products){
        List<ResponseProduct> responseProducts = new ArrayList<>();

        for (ProductListDto product : products) {
            String[] tags = getTagsById(product.getProductId());
            String imgLink = toImgLink(product.getPicture());

            ResponseProduct responseProduct =
                    ResponseProduct.builder().
                            productId(product.getProductId())
                            .picture(imgLink)
                            .tags(tags)
                            .title(product.getTitle())
                            .artist(product.getArtistName())
                            .build();

            responseProducts.add(responseProduct);
        }

        if(responseProducts.isEmpty()) {
            throw new NoSearchException("키워드에 해당하는 게시물이 없습니다.");
        }

        return responseProducts;
    }

    private ResponseProductDetail toReponseBuild(long productId, boolean isQr){
        if(isExistsProduct(productId)){
            //리턴 메세지
            String message = addView(productId, isQr);

            //기본 값
            ProductDto productDto = productMapper.getProductById(productId);

            //사진 설명 세팅
            PictureInfo pictureInfo = getPictureInfo(productId);
            //인스타그램 전체 uri 세팅
            String instagramUri = toInstagramUri(productDto.getArtistInstagramId());
            //태그 값 세팅
            String[] tags = getTagsById(productId);
            //이미지 값 세팅
            String imgLink = toImgLink(productDto.getPicture());

            return ResponseProductDetail.builder().
                    productId(productDto.getProductId()).
                    picture(imgLink).
                    title(productDto.getTitle()).
                    tags(tags).
                    artist(productDto.getArtistName()).
                    pictureInfo(pictureInfo).
                    description(productDto.getDescription()).
                    instar(instagramUri).
                    price(productDto.getPrice())
                    .message(message).build();
        } else {
            throw new NoExistProductException("해당하는 게시물이 없습니다.");
        }
    }

    private PictureInfo getPictureInfo(long productId){
        return productMapper.getPictureInfoById(productId);
    }

    private String[] getTagsById(long productId) {
        return productMapper.getTagsById(productId);
    }

    private String toInstagramUri(String artistId){
        return Path.INSTAGRAM_URI + artistId;
    }

    private String toImgLink(String picture){
        return Path.IMG_BUCKET_PATH + picture;
    }

    private boolean isExistsProduct(long postId){
        return productMapper.isExistsProduct(postId);
    }
}
