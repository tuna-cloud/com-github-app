package com.github.app.api.dal.dao;

import com.github.app.api.dal.domain.Popedom;
import com.github.app.api.dal.domain.PopedomExample;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PopedomMapper {
    long countByExample(PopedomExample example);

    int deleteByExample(PopedomExample example);

    int deleteByPrimaryKey(Integer number);

    int insert(Popedom record);

    int insertSelective(Popedom record);

    List<Popedom> selectByExample(PopedomExample example);

    Popedom selectByPrimaryKey(Integer number);

    int updateByExampleSelective(@Param("record") Popedom record, @Param("example") PopedomExample example);

    int updateByExample(@Param("record") Popedom record, @Param("example") PopedomExample example);

    int updateByPrimaryKeySelective(Popedom record);

    int updateByPrimaryKey(Popedom record);
}