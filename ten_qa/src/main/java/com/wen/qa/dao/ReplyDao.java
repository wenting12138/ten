package com.wen.qa.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.wen.common.model.Reply;
/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface ReplyDao extends JpaRepository<Reply,String>,JpaSpecificationExecutor<Reply>{
	
}
