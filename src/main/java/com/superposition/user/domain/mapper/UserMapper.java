package com.superposition.user.domain.mapper;

import com.superposition.user.domain.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {
    boolean isExistUserByEmail(@Param("email") String email);

    void saveUserInfo(User user);
}
