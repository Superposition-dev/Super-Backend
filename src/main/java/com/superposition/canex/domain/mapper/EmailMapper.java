package com.superposition.canex.domain.mapper;

import com.superposition.canex.domain.entity.Email;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EmailMapper {

    void save(Email email);
}
