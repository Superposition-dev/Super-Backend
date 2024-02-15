package com.superposition.follow.domain.mapper;

import com.superposition.follow.domain.entity.ArtistFollow;
import com.superposition.follow.dto.ArtistFollowDto;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ArtistFollowMapper {
    public void save(ArtistFollowDto dto);

    public int getFollowCount(@Param("email") String email);

    public boolean isExistFollowBy(ArtistFollowDto dto);

    public List<ArtistFollow> findByEmail(@Param("email") String email);

    void deleteBy(ArtistFollowDto dto);
}
