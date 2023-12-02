package com.superposition.artist.domain.mapper;

import com.superposition.artist.dto.ArtistInfo;
import com.superposition.artist.dto.ResponseArtist;
import com.superposition.artist.dto.ResponseDisplayArtist;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ArtistMapper {
    public boolean isExistsArtist(@Param("instagramId") String instagramId);
    public boolean isExistsResult(@Param("name") String name);
    public List<ResponseDisplayArtist> getAboutArtist();
    public List<ResponseArtist> getAllArtist();
    public List<ResponseArtist> getArtistByKeyword(@Param("name") String name);
    public List<ResponseDisplayArtist> getDisplayArtist();
    public ArtistInfo getArtistInfoById(@Param("instagramId") String instagramId);
    public void addViewCountById(@Param("instagramId") String instagramId);
    public int getViewCount(@Param("instagramId") String instagramId);
}
