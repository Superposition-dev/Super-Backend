package com.superposition.artist.domain.mapper;

import com.superposition.artist.domain.entity.ArtistFollow;
import com.superposition.artist.dto.ArtistFollowDto;
import com.superposition.artist.dto.ArtistInfo;
import com.superposition.artist.dto.ResponseArtist;
import com.superposition.artist.dto.ResponseDisplayArtist;
import java.util.List;
import java.util.Optional;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ArtistFollowMapper {
    public void save(ArtistFollowDto dto);

    public int getFollowCount(@Param("email") String email);

    public boolean isExistFollowBy(ArtistFollowDto dto);

    public List<ArtistFollow> findByEmail(@Param("email") String email);
}
