package com.superposition.art.domain.mapper;

import com.superposition.art.domain.entity.Exhibition;
import com.superposition.art.domain.entity.ExhibitionStatus;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ExhibitionMapper {

    int getTotalCount();

    List<Exhibition> findExhibitions(@Param("offset") int offset, @Param("limit") int limit,
                                     @Param("status") ExhibitionStatus status);
}
