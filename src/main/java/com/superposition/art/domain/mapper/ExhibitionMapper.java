package com.superposition.art.domain.mapper;

import com.superposition.art.domain.entity.Exhibition;
import com.superposition.art.domain.entity.ExhibitionStatus;
import java.util.List;
import java.util.Optional;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ExhibitionMapper {

    int getTotalCount();

    List<Exhibition> findExhibitions(@Param("offset") int offset, @Param("limit") int limit,
                                     @Param("status") ExhibitionStatus status);

    List<Exhibition> findExhibitionsByStatus(@Param("status") ExhibitionStatus status);

    Optional<Exhibition> findExhibitionById(@Param("exhibition_id") long exhibitionId);
}
