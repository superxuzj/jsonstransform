package com.boliangshenghe.jsontransform.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.boliangshenghe.jsontransform.entity.Jdata;

@Mapper
public interface JdataMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Jdata record);

    int insertSelective(Jdata record);

    Jdata selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Jdata record);

    int updateByPrimaryKey(Jdata record);
    
    List<Jdata> selectJdataList(Jdata record);
    
    List<Jdata> selectJdataEventIDList(Jdata record);
}