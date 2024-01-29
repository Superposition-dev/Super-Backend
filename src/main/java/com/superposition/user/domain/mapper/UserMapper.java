package com.superposition.user.domain.mapper;

import com.superposition.user.domain.entity.User;
import com.superposition.user.dto.RequestUserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;

@Mapper
public interface UserMapper {
    boolean isExistUserByEmail(@Param("email") String email);

    void saveUserInfo(User user);

    RequestUserInfo getUserInfoByEmail(@Param("email") String email);

    boolean isAvailableChange(@Param("email") String email);
}
