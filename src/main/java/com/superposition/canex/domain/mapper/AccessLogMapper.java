package com.superposition.canex.domain.mapper;

import com.superposition.canex.domain.entity.AccessLog;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AccessLogMapper {

    void save(AccessLog accessLog);
}
