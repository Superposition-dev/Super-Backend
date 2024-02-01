package com.superposition.user.domain.mapper;

import com.superposition.user.domain.entity.User;
import com.superposition.user.dto.ResponseUserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {
    boolean isExistUserByEmail(@Param("email") String email);

    void saveUserInfo(User user);

    ResponseUserInfo getUserInfoByEmail(@Param("email") String email);

    boolean isAvailableChange(@Param("email") String email);

    void updateUserProfile(@Param("email") String email, @Param("profile") String profile);

    void deleteUserByEmail(@Param("email") String email);
}
