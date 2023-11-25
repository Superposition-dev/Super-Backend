package com.superposition.artist.domain.mapper;

import com.superposition.artist.dto.ArtistInfo;
import com.superposition.artist.dto.ResponseArtist;
import com.superposition.artist.dto.ResponseDisplayArtist;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ArtistMapper {
    public boolean isExistsArtist(@Param("name") String name);
    public boolean isExistsResult(@Param("keyword") String keyword);
    public List<ResponseArtist> getAllArtist();
    public List<ResponseArtist> getArtistByKeyword(@Param("name") String name);
    public List<ResponseDisplayArtist> getDisplayArtist();
    public ArtistInfo getArtistInfoByName(@Param("name") String name);
    public void addViewCountByName(@Param("name") String name);
}
