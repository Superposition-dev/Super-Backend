package com.superposition.art.service;

import com.superposition.art.domain.entity.Exhibition;
import com.superposition.art.domain.entity.ExhibitionStatus;
import com.superposition.art.domain.mapper.ExhibitionMapper;
import com.superposition.art.dto.ResponseExhibition;
import com.superposition.product.utils.PageInfo;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ExhibitionServiceImpl implements ExhibitionService {

    private final ExhibitionMapper exhibitionMapper;

    public ExhibitionServiceImpl(ExhibitionMapper exhibitionMapper) {
        this.exhibitionMapper = exhibitionMapper;
    }

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
}
