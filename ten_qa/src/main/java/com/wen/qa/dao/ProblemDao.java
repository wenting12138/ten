package com.wen.qa.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.wen.common.model.Problem;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface ProblemDao extends JpaRepository<Problem,String>,JpaSpecificationExecutor<Problem>{

    // 最新的问题
    @Query(value = "SELECT p.* FROM tb_problem p LEFT JOIN tb_pl l ON l.problemid = p.id WHERE l.labelid = ? ORDER BY p.replytime DESC", nativeQuery = true)
    Page<Problem> newList(String labelId, Pageable pageable);

    // 最热门的问题
    @Query(value = "SELECT p.* FROM tb_problem p LEFT JOIN tb_pl l ON l.problemid = p.id WHERE l.labelid = ? ORDER BY p.reply DESC", nativeQuery = true)
    Page<Problem> hotList(String labelId, Pageable pageable);

    // 等待回答的问题
    @Query(value = "SELECT p.* FROM tb_problem p LEFT JOIN tb_pl l ON l.problemid = p.id WHERE l.labelid = ? and l.reply = 0 ORDER BY p.createtime DESC", nativeQuery = true)
    Page<Problem> waitList(String labelId, Pageable pageable);
}
