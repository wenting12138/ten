package com.wen.notice.dao;

import com.wen.common.model.Notice;
import com.wen.common.model.NoticeFresh;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface NoticeFreshMapper {

    void save(NoticeFresh noticeFresh);

    NoticeFresh findOneById(String id);

    List<NoticeFresh> findByUserPage(@Param("id") String userId,@Param("page") int page,@Param("size") int size);

    void delNoticeFresh(NoticeFresh fresh);
}
