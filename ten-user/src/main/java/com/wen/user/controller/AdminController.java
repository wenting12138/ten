package com.wen.user.controller;

import java.util.HashMap;
import java.util.Map;

import com.wen.common.model.Admin;
import com.wen.common.result.PageResult;
import com.wen.common.result.Result;
import com.wen.common.result.ResultCode;
import com.wen.common.result.ResultService;
import com.wen.common.constant.UserConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import com.wen.user.service.AdminService;

/**
 * 控制器层
 * @author Administrator
 *
 */
@RestController
@CrossOrigin
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private AdminService adminService;



	@PostMapping("/login")
	public Result login(@RequestBody Admin admin){
		if (admin == null || admin.getLoginname() == null || admin.getPassword() == null) {
			return Result.ok(ResultCode.FAIL, null);
		}
		ResultService<String> service = adminService.login(admin);
		if (service.isB()) {

			// 取出jwtToken
			String jwtToken = service.getRows();
			Map<String, Object> map = new HashMap<>();
			map.put("token", jwtToken);
			map.put("role", UserConstant.admin);
			return Result.ok(ResultCode.SUCCESS, map);
		}
		return Result.ok(ResultCode.FAIL, null);
	}
	
	
	/**
	 * 查询全部数据
	 * @return
	 */
	@RequestMapping(method= RequestMethod.GET)
	public Result findAll(){
		return Result.ok(ResultCode.SUCCESS,adminService.findAll());
	}
	
	/**
	 * 根据ID查询
	 * @param id ID
	 * @return
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.GET)
	public Result findById(@PathVariable String id){
		return Result.ok(ResultCode.SUCCESS,adminService.findById(id));
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
		Page<Admin> pageList = adminService.findSearch(searchMap, page, size);
		return Result.ok(ResultCode.SUCCESS,  new PageResult<Admin>(pageList.getTotalElements(), pageList.getContent()) );
	}

	/**
     * 根据条件查询
     * @param searchMap
     * @return
     */
    @RequestMapping(value="/search",method = RequestMethod.POST)
    public Result findSearch( @RequestBody Map searchMap){
        return Result.ok(ResultCode.SUCCESS,adminService.findSearch(searchMap));
    }
	
	/**
	 * 增加
	 * @param admin
	 */
	@RequestMapping(method=RequestMethod.POST)
	public Result add(@RequestBody Admin admin  ){
		adminService.add(admin);
		return Result.ok(ResultCode.SUCCESS, null);
	}
	
	/**
	 * 修改
	 * @param admin
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.PUT)
	public Result update(@RequestBody Admin admin, @PathVariable String id ){
		admin.setId(id);
		adminService.update(admin);		
		return Result.ok(ResultCode.SUCCESS, null);
	}
	
	/**
	 * 删除
	 * @param id
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.DELETE)
	public Result delete(@PathVariable String id ){
		adminService.deleteById(id);
		return Result.ok(ResultCode.SUCCESS, null);
	}
	
}
