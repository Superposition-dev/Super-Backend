package com.superposition.product.service;

import com.superposition.product.domain.mapper.ProductMapper;
import com.superposition.product.dto.*;
import com.superposition.product.exception.NoExistProductException;
import com.superposition.product.exception.NoSearchException;
import com.superposition.product.utils.Path;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductMapper productMapper;

    public ProductServiceImpl(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    @Override
    public List<ResponseProduct> getAllProducts(String search) {
        if (!search.trim().isBlank()) {
            return toResponseProducts(searchByKeyword(search));
        } else {
            return toResponseProducts(productMapper.getAllProducts());
        }
    }

    @Override
    public ResponseProductDetail getProductById(String productId, boolean isQr) {
        return toResponseBuild(productId, isQr);
    }

    @Override
    public List<Map<String, String>> getProductByName(String name) {
        return productMapper.getProductByName(name);
    }

    @Override
    public Payload likeProduct(String productId, boolean isLike) {
        if (isPlus(productId)) {
            if (isLike) {
                productMapper.likeProduct(productId);
                return Payload.builder().like(true).build();
            } else {
                productMapper.disLikeProduct(productId);
                return Payload.builder().like(false).build();
            }
        } else {
            return null;
        }
    }

    @Override
    public void orderClickCount(String productId) {
        productMapper.orderClickCount(productId);
    }

    @Override
    public void addView(String productId) {
        productMapper.addBasicView(productId);
    }

    private String addView(String productId, boolean isQr) {
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
            throw new NoSearchException("키워드에 해당하는 게시물이 없습니다.");
        }

        return responseProducts;
    }

    private ResponseProductDetail toResponseBuild(String productId, boolean isQr) {
        if (isExistsProduct(productId)) {
            //리턴 메세지
            String message = addView(productId, isQr);

            //기본 값
            ProductDto productDto = productMapper.getProductById(productId);

            //사진 설명 세팅
            PictureInfo pictureInfo = getPictureInfo(productId);
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
                    price(productDto.getPrice())
                    .message(message).build();
        } else {
            throw new NoExistProductException("해당하는 게시물이 없습니다.");
        }
    }

    private PictureInfo getPictureInfo(String productId) {
        return productMapper.getPictureInfoById(productId);
    }

    private String[] getTagsById(String productId) {
        return productMapper.getTagsById(productId);
    }

    private boolean isExistsProduct(String productId) {
        return productMapper.isExistsProduct(productId);
    }

    private boolean isPlus(String productId) {
        int likeCount = productMapper.getLikeCount(productId);
        if (likeCount >= 0) {
            return true;
        } else {
            return false;
        }
    }
}
