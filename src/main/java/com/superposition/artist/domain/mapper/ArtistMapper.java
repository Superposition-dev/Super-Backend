package com.superposition.artist.domain.mapper;

import com.superposition.artist.dto.ArtistInfo;
import com.superposition.artist.dto.ResponseArtist;
import com.superposition.artist.dto.ResponseDisplayArtist;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ArtistMapper {
    public List<ResponseArtist> getAllArtist();
    public List<ResponseArtist> getArtistByKeyword(@Param("name") String name);
    public List<ResponseDisplayArtist> getDisplayArtist();
    public ArtistInfo getArtistInfoByName(String name);
}
