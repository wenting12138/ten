package com.wen.recruit.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.wen.common.model.Recruit;

import java.util.List;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface RecruitDao extends JpaRepository<Recruit,String>,JpaSpecificationExecutor<Recruit>{
	// 推荐职位
    List<Recruit> findTop6ByStateOOrderByCreatetimeDesc(String state);

    List<Recruit> findTop6ByStateNotOrOrderByCreatetimeDesc(String state);
}
