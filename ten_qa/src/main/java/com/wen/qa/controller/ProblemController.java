package com.wen.qa.controller;

import java.util.List;
import java.util.Map;

import com.wen.common.model.User;
import com.wen.common.result.PageResult;
import com.wen.common.result.Result;
import com.wen.common.result.ResultCode;
import com.wen.common.result.ResultService;
import com.wen.qa.interceptor.JwtInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import com.wen.common.model.Problem;
import com.wen.qa.service.ProblemService;

/**
 * 控制器层
 * @author Administrator
 *
 */
@RestController
@CrossOrigin
@RequestMapping("/problem")
public class ProblemController {

	@Autowired
	private ProblemService problemService;
	
	
	/**
	 * 查询全部数据
	 * @return
	 */
	@RequestMapping(method= RequestMethod.GET)
	public Result findAll(){
		return Result.ok(ResultCode.SUCCESS,problemService.findAll());
	}
	
	/**
	 * 根据ID查询
	 * @param id ID
	 * @return
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.GET)
	public Result findById(@PathVariable String id){
		return Result.ok(ResultCode.SUCCESS,problemService.findById(id));
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
		Page<Problem> pageList = problemService.findSearch(searchMap, page, size);
		return Result.ok(ResultCode.SUCCESS,  new PageResult<Problem>(pageList.getTotalElements(), pageList.getContent()) );
	}

	/**
     * 根据条件查询
     * @param searchMap
     * @return
     */
    @RequestMapping(value="/search",method = RequestMethod.POST)
    public Result findSearch( @RequestBody Map searchMap){
        return Result.ok(ResultCode.SUCCESS,problemService.findSearch(searchMap));
    }
	
	/**
	 * 增加
	 * @param problem
	 */
	@RequestMapping(method=RequestMethod.POST)
	public Result add(@RequestBody Problem problem  ){
		User userInfo = JwtInterceptor.getUserInfo();
		if (userInfo.getJwtToken() == null || userInfo.getJwtToken().equals("")) {
			return Result.ok(ResultCode.INSUFFICIENTPERMISSIONS, null);
		}
		problemService.add(problem);
		return Result.ok(ResultCode.SUCCESS,null);
	}
	
	/**
	 * 修改
	 * @param problem
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.PUT)
	public Result update(@RequestBody Problem problem, @PathVariable String id ){
		problem.setId(id);
		problemService.update(problem);		
		return Result.ok(ResultCode.SUCCESS,null);
	}
	
	/**
	 * 删除
	 * @param id
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.DELETE)
	public Result delete(@PathVariable String id ){
		problemService.deleteById(id);
		return Result.ok(ResultCode.SUCCESS, null);
	}

	/**
	 *  最新回答
	 */
	@GetMapping("/newlist/{labelId}/{page}/{size}")
	public Result newList(@PathVariable("labelId") String labelId,
						  @PathVariable("page") Integer page,
						  @PathVariable("size") Integer size){
		ResultService<PageResult<Problem>> service = problemService.newList(labelId, page, size);
		if (service.isB()) {
			return Result.ok(ResultCode.SUCCESS, service.getRows());
		}
		return Result.ok(ResultCode.SUCCESS, null);
	}


	/**
	 *  最热的回答
	 */
	@GetMapping("/newlist/{labelId}/{page}/{size}")
	public Result hotlist(@PathVariable("labelId") String labelId,
						  @PathVariable("page") Integer page,
						  @PathVariable("size") Integer size){
		ResultService<PageResult<Problem>> service = problemService.hotlist(labelId, page, size);
		if (service.isB()) {
			return Result.ok(ResultCode.SUCCESS, service.getRows());
		}
		return Result.ok(ResultCode.SUCCESS, null);
	}

	/**
	 *  等待的回答
	 */
	@GetMapping("/newlist/{labelId}/{page}/{size}")
	public Result waitlist(@PathVariable("label") String labelId,
						  @PathVariable("page") Integer page,
						  @PathVariable("size") Integer size){
		ResultService<PageResult<Problem>> service = problemService.waitlist(labelId, page, size);
		if (service.isB()) {
			return Result.ok(ResultCode.SUCCESS, service.getRows());
		}
		return Result.ok(ResultCode.SUCCESS, null);
	}
	
}