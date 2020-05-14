package com.wen.base.mapper;

import com.wen.common.model.Label;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface LabelMapper {

    List<Label> selectByConditionPage(@Param("label") Label label, @Param("page") Integer page, @Param("size") Integer size);

    List<Label> selectByCondition(Label label);

    List<Label> selectTopList(String recommend);

    List<Label> selectList(String state);
}
