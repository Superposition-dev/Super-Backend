package com.superposition.productexhibition.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProductExhibitionMapper {
    List<Long> getProductIdsByExhibitionId(long exhibitionId);
}
