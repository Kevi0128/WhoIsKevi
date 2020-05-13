package com.kevi.code.dao;

import com.kevi.code.entity.KeviWorkInfo;
import com.kevi.code.entity.KeviWorkInfoExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface KeviWorkInfoMapper {
    long countByExample(KeviWorkInfoExample example);

    int deleteByExample(KeviWorkInfoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(KeviWorkInfo record);

    int insertSelective(KeviWorkInfo record);

    List<KeviWorkInfo> selectByExample(KeviWorkInfoExample example);

    KeviWorkInfo selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") KeviWorkInfo record, @Param("example") KeviWorkInfoExample example);

    int updateByExample(@Param("record") KeviWorkInfo record, @Param("example") KeviWorkInfoExample example);

    int updateByPrimaryKeySelective(KeviWorkInfo record);

    int updateByPrimaryKey(KeviWorkInfo record);
}