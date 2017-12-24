package com.github.app.api.dao.mapper;

import com.github.app.api.dao.domain.Page;
import com.github.app.api.dao.domain.PageExample;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PageMapper {
    long countByExample(PageExample example);

    int deleteByExample(PageExample example);

    int deleteByPrimaryKey(Integer number);

    int insert(Page record);

    int insertSelective(Page record);

    List<Page> selectByExample(PageExample example);

    Page selectByPrimaryKey(Integer number);

    int updateByExampleSelective(@Param("record") Page record, @Param("example") PageExample example);

    int updateByExample(@Param("record") Page record, @Param("example") PageExample example);

    int updateByPrimaryKeySelective(Page record);

    int updateByPrimaryKey(Page record);
}