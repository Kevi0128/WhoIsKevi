package com.kevi.code.dao.query;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface KeviWorkInfoCustomQuery {

    String getInfoByKey(@Param("key") String key);

}
