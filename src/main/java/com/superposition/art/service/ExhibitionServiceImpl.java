package com.superposition.art.service;

import com.superposition.art.domain.entity.Exhibition;
import com.superposition.art.domain.entity.ExhibitionStatus;
import com.superposition.art.domain.mapper.ExhibitionMapper;
import com.superposition.art.dto.ResponseExhibition;
import com.superposition.art.dto.ResponseExhibitionDetail;
import com.superposition.art.exception.NoExistExhibitionException;
import com.superposition.artist.domain.mapper.ArtistMapper;
import com.superposition.artist.dto.ArtistInfo;
import com.superposition.product.domain.mapper.ProductMapper;
import com.superposition.product.dto.ProductDto;
import com.superposition.product.dto.ResponseProduct;
import com.superposition.product.utils.PageInfo;
import com.superposition.utils.exception.NoSearchException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ExhibitionServiceImpl implements ExhibitionService {

    private final ExhibitionMapper exhibitionMapper;
    private final ProductMapper productMapper;
    private final ArtistMapper artistMapper;

    @Override
    @Transactional(readOnly = true)
    public PageInfo<ResponseExhibition> getExhibitions(final Pageable pageable, final ExhibitionStatus status) {
        int offset = (pageable.getPageNumber() - 1) * pageable.getPageSize();
        int limit = pageable.getPageSize();

        int totalCount = exhibitionMapper.getTotalCount();
        List<Exhibition> findExhibitions = exhibitionMapper.findExhibitions(offset, limit, status);
        List<ResponseExhibition> data = findExhibitions.stream()
                .map(ResponseExhibition::from)
                .toList();
        return PageInfo.<ResponseExhibition>builder()
                .pageIndex(pageable.getPageNumber())
                .pageSize(pageable.getPageSize())
                .totalCount(totalCount)
                .data(data)
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseExhibitionDetail getExhibitionById(long exhibitionId) {
        Exhibition exhibition = exhibitionMapper.findExhibitionById(exhibitionId)
                .orElseThrow(NoExistExhibitionException::new);

        List<ProductDto> productDtos = productMapper.getProductsByExhibitionId(exhibition.getId());
        List<ResponseProduct> responseProducts = toResponseProducts(productDtos);

        List<ArtistInfo> artistInfos = productDtos.stream()
                .map(ProductDto::getInstagramId)
                .map(artistMapper::getArtistInfoById)
                .toList();

        return ResponseExhibitionDetail.builder()
                .exhibitionId(exhibitionId)
                .title(exhibition.getTitle())
                .subHeading(exhibition.getSubHeading())
                .productInfo(responseProducts)
                .artistInfo(artistInfos)
                .build();
    }

    private List<ResponseProduct> toResponseProducts(List<ProductDto> products) {
        List<ResponseProduct> responseProducts = products.stream()
                .map(productDto -> {
                    String[] tags = productMapper.getTagsById(productDto.getProductId());
                    return ResponseProduct.builder().
                            productId(productDto.getProductId())
                            .picture(productDto.getPicture())
                            .tags(tags)
                            .title(productDto.getTitle())
                            .artist(productDto.getArtistName())
                            .build();
                })
                .toList();

        if (responseProducts.isEmpty()) {
            throw new NoSearchException();
        }

        return responseProducts;
    }
}
