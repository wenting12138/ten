package com.wen.notice.dao;

import com.wen.common.model.Notice;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface NoticeMapper {

    void save(Notice notice);

    Notice findOneById(String id);

    List<Notice> selectPageCondition(@Param("notice") Notice notice, @Param("page")int page, @Param("size") int size);

    void update(Notice notice);
}
