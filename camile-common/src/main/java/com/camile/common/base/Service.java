package com.camile.common.base;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 服务基础接口
 * @param <Record>
 * @param <Example>
 */
public interface Service<Record, Example> {
    int countByExample(Example example);

    int deleteByExample(Example example);

    int deleteByPrimaryKey(Integer id);

    int insert(Record record);

    int insertSelective(Record record);

    List<Record> selectByExampleWithBLOBs(Example example);

    List<Record> selectByExample(Example example);

    List<Record> selectByExampleWithBLOBsForStartPage(Example example, Integer pageNum, Integer pageSize);

    List<Record> selectByExampleForStartPage(Example example, Integer pageNum, Integer pageSize);

    List<Record> selectByExampleWithBLOBsForOffsetPage(Example example, Integer offset, Integer limit);

    List<Record> selectByExampleForOffsetPage(Example example, Integer offset, Integer limit);

    Record selectFirstByExample(Example example);

    Record selectFirstByExampleWithBLOBs(Example example);

    Record selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Record record, @Param("example") Example example);

    int updateByExampleWithBLOBs(@Param("record") Record record, @Param("example") Example example);

    int updateByExample(@Param("record") Record record, @Param("example") Example example);

    int updateByPrimaryKeySelective(Record record);

    int updateByPrimaryKeyWithBLOBs(Record record);

    int updateByPrimaryKey(Record record);

    int deleteByPrimaryKeys(String ids);

    void initMapper();
}
