package com.superposition.art.service;

import com.superposition.art.domain.entity.ExhibitionStatus;
import com.superposition.art.dto.ResponseExhibition;
import com.superposition.art.dto.ResponseExhibitionDetail;
import com.superposition.product.utils.PageInfo;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface ExhibitionService {
    PageInfo<ResponseExhibition> getExhibitions(final Pageable pageable, final ExhibitionStatus status);

    List<ResponseExhibition> getExhibitionsByStatus(final ExhibitionStatus status);

    ResponseExhibitionDetail getExhibitionById(long exhibitionId);
}
