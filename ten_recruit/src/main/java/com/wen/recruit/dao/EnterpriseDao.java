package com.wen.recruit.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.wen.common.model.Enterprise;

import java.util.List;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface EnterpriseDao extends JpaRepository<Enterprise,String>,JpaSpecificationExecutor<Enterprise>{

    // 查询热门的企业
    List<Enterprise> findByIshot(String isHost);

}
