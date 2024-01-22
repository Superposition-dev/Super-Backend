package com.superposition.user.domain.mapper;

import com.superposition.user.dto.KakaoResponse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {
    boolean isExistUserByEmail(@Param("email") String user);
}
