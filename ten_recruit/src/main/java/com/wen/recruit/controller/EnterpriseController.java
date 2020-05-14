package com.wen.recruit.controller;

import java.util.Map;
import com.wen.common.result.PageResult;
import com.wen.common.result.Result;
import com.wen.common.result.ResultCode;
import com.wen.recruit.constants.EnterpriseConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import com.wen.common.model.Enterprise;
import com.wen.recruit.service.EnterpriseService;
/**
 * 控制器层
 * @author Administrator
 *
 */
@RestController
@CrossOrigin
@RequestMapping("/enterprise")
public class EnterpriseController {

	@Autowired
	private EnterpriseService enterpriseService;

	/**
	 * 查询全部数据
	 * @return
	 */
	@RequestMapping(method= RequestMethod.GET)
	public Result findAll(){
		return Result.ok(ResultCode.SUCCESS,enterpriseService.findAll());
	}
	
	/**
	 * 根据ID查询
	 * @param id ID
	 * @return
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.GET)
	public Result findById(@PathVariable String id){
		return Result.ok(ResultCode.SUCCESS,enterpriseService.findById(id));
	}


	/**
	 * 分页+多条件查询
	 * @param searchMap 查询条件封装
	 * @param page 页码
	 * @param size 页大小
	 * @return 分页结果
	 */
	@RequestMapping(value="/search/{page}/{size}",method=RequestMethod.POST)
	public Result findSearch(@RequestBody Map searchMap , @PathVariable int page, @PathVariable int size){
		Page<Enterprise> pageList = enterpriseService.findSearch(searchMap, page, size);
		return Result.ok(ResultCode.SUCCESS,  new PageResult<Enterprise>(pageList.getTotalElements(), pageList.getContent()) );
	}

	/**
     * 根据条件查询
     * @param searchMap
     * @return
     */
    @RequestMapping(value="/search",method = RequestMethod.POST)
    public Result findSearch( @RequestBody Map searchMap){
        return Result.ok(ResultCode.SUCCESS,enterpriseService.findSearch(searchMap));
    }
	
	/**
	 * 增加
	 * @param enterprise
	 */
	@RequestMapping(method=RequestMethod.POST)
	public Result add(@RequestBody Enterprise enterprise){
		enterpriseService.add(enterprise);
		return Result.ok(ResultCode.SUCCESS,null);
	}
	
	/**
	 * 修改
	 * @param enterprise
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.PUT)
	public Result update(@RequestBody Enterprise enterprise, @PathVariable String id ){
		enterprise.setId(id);
		enterpriseService.update(enterprise);		
		return Result.ok(ResultCode.SUCCESS,null);
	}
	
	/**
	 * 删除
	 * @param id
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.DELETE)
	public Result delete(@PathVariable String id ){
		enterpriseService.deleteById(id);
		return Result.ok(ResultCode.SUCCESS,null);
	}


	/**
	 *  热门企业
	 */
	@GetMapping("/search/hotlist")
	public Result getIsHot(){
		return Result.ok(ResultCode.SUCCESS, enterpriseService.hotList(EnterpriseConstant.hot));
	}

}
