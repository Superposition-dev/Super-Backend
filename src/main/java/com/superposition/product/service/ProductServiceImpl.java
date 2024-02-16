package com.superposition.product.service;

import com.superposition.like.service.LikeService;
import com.superposition.product.domain.mapper.ProductMapper;
import com.superposition.product.dto.*;
import com.superposition.product.exception.NoExistProductException;
import com.superposition.utils.exception.NoSearchException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductMapper productMapper;
    private final LikeService likeService;

    @Override
    public List<ResponseProduct> getAllProducts(String search) {
        if (!search.trim().isBlank()) {
            return toResponseProducts(searchByKeyword(search));
        } else {
            return toResponseProducts(productMapper.getAllProducts());
        }
    }

    @Override
    public ResponseProductDetail getProductById(long productId, boolean isQr, UserDetails user) {
        if (user != null){
            return toResponseBuild(productId, isQr, user.getUsername());
        } else {
            return toResponseBuild(productId, isQr, null);
        }
    }

    @Override
    public List<SimpleProduct> getProductByName(String name) {
        return productMapper.getProductByName(name);
    }

    @Override
    public void likeProduct(long productId, String email) {
        likeService.likeProduct(productId, email);
    }

    @Override
    public void dislikeProduct(long productId, String email) {
        likeService.dislikeProduct(productId, email);
    }

    @Override
    public void orderClickCount(long productId) {
        productMapper.orderClickCount(productId);
    }

    @Override
    public void addView(long productId) {
        productMapper.addBasicView(productId);
    }

    @Override
    public ResponseProduct getProductInfo(long productId){
        String[] tags = getTagsById(productId);
        ProductListDto productInfo = productMapper.getProductInfo(productId);
        return ResponseProduct.builder()
                .productId(productId)
                .picture(productInfo.getPicture())
                .tags(tags)
                .title(productInfo.getTitle())
                .artist(productInfo.getArtistName()).build();
    }

    private List<ResponseProduct> toResponseProducts(List<ProductListDto> products) {
        List<ResponseProduct> responseProducts = new ArrayList<>(products.size());

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

        if (responseProducts.isEmpty()) {
            throw new NoSearchException();
        }

        return responseProducts;
    }

    private String addView(long productId, boolean isQr) {
        if (isQr) {
            productMapper.addQrView(productId);
            return "QR 코드가 인정되었습니다.";
        } else {
            return "일반 조회입니다.";
        }
    }

    private List<ProductListDto> searchByKeyword(String keyword) {
        return productMapper.getProductsByKeyword(keyword);
    }

    private ResponseProductDetail toResponseBuild(long productId, boolean isQr, String email) {
        if (isExistsProduct(productId)) {
            //리턴 메세지
            String message = addView(productId, isQr);

            //기본 값
            ProductDto productDto = productMapper.getProductById(productId);

            //사진 설명 세팅
            PictureInfo pictureInfo = getPictureInfo(productId);
            //태그 값 세팅
            String[] tags = getTagsById(productId);
            //작가 정보 세팅
            ArtistInfoInProduct artistInfo = buildInfo(productDto.getArtistName(), productDto.getInstagramId(), productDto.getProfile());

            boolean like = false;
            if(StringUtils.hasText(email)) like = likeService.isLike(productId, email);

            return ResponseProductDetail.builder().
                    productId(productDto.getProductId()).
                    picture(productDto.getPicture()).
                    title(productDto.getTitle()).
                    tags(tags).
                    artistInfo(artistInfo).
                    pictureInfo(pictureInfo).
                    description(productDto.getDescription()).
                    price(productDto.getPrice())
                    .isLike(like)
                    .message(message).build();
        } else {
            throw new NoExistProductException("해당하는 게시물이 없습니다.");
        }
    }

    private ArtistInfoInProduct buildInfo(String name, String instagramId, String profile){
        return ArtistInfoInProduct.builder()
                .artistName(name)
                .instagramId(instagramId)
                .profile(profile).build();
    }

    private PictureInfo getPictureInfo(long productId) {
        return productMapper.getPictureInfoById(productId);
    }

    private String[] getTagsById(long productId) {
        return productMapper.getTagsById(productId);
    }

    private boolean isExistsProduct(long productId) {
        return productMapper.isExistsProduct(productId);
    }
}
