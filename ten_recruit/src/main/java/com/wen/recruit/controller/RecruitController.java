package com.wen.recruit.controller;

import java.util.List;
import java.util.Map;
import com.wen.common.result.PageResult;
import com.wen.common.result.Result;
import com.wen.common.result.ResultCode;
import com.wen.common.result.ResultService;
import com.wen.recruit.constants.RecruitConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import com.wen.common.model.Recruit;
import com.wen.recruit.service.RecruitService;

/**
 * 控制器层
 * @author Administrator
 *
 */
@RestController
@CrossOrigin
@RequestMapping("/recruit")
public class RecruitController {

	@Autowired
	private RecruitService recruitService;
	
	
	/**
	 * 查询全部数据
	 * @return
	 */
	@RequestMapping(method= RequestMethod.GET)
	public Result findAll(){
		return Result.ok(ResultCode.SUCCESS,recruitService.findAll());
	}
	
	/**
	 * 根据ID查询
	 * @param id ID
	 * @return
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.GET)
	public Result findById(@PathVariable String id){
		return Result.ok(ResultCode.SUCCESS,recruitService.findById(id));
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
		Page<Recruit> pageList = recruitService.findSearch(searchMap, page, size);
		return Result.ok(ResultCode.SUCCESS,  new PageResult<Recruit>(pageList.getTotalElements(), pageList.getContent()) );
	}

	/**
     * 根据条件查询
     * @param searchMap
     * @return
     */
    @RequestMapping(value="/search",method = RequestMethod.POST)
    public Result findSearch( @RequestBody Map searchMap){
        return Result.ok(ResultCode.SUCCESS,recruitService.findSearch(searchMap));
    }
	
	/**
	 * 增加
	 * @param recruit
	 */
	@RequestMapping(method=RequestMethod.POST)
	public Result add(@RequestBody Recruit recruit  ){
		recruitService.add(recruit);
		return Result.ok(ResultCode.SUCCESS,null);
	}
	
	/**
	 * 修改
	 * @param recruit
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.PUT)
	public Result update(@RequestBody Recruit recruit, @PathVariable String id ){
		recruit.setId(id);
		recruitService.update(recruit);		
		return Result.ok(ResultCode.SUCCESS,null);
	}
	
	/**
	 * 删除
	 * @param id
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.DELETE)
	public Result delete(@PathVariable String id ){
		recruitService.deleteById(id);
		return Result.ok(ResultCode.SUCCESS,null);
	}

	/**
	 *  推荐职位
	 */
	@GetMapping("/search/recommend")
	public Result recommendList(){
		ResultService<List<Recruit>> service = recruitService.list(RecruitConstant.state_good);
		if (service.isB()) {
			return Result.ok(ResultCode.SUCCESS, service.getRows());
		}
		return Result.ok(ResultCode.FAIL, null);
	}

	/**
	 *  最新职位
	 */
	@GetMapping("/search/newlist")
	public Result newlist(){
		ResultService<List<Recruit>> service = recruitService.newList(RecruitConstant.state_close);
		if (service.isB()) {
			return Result.ok(ResultCode.SUCCESS, service.getRows());
		}
		return Result.ok(ResultCode.FAIL, null);
	}
	
}
