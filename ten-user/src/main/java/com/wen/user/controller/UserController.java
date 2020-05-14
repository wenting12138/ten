package com.wen.user.controller;

import java.util.HashMap;
import java.util.Map;

import com.wen.common.model.User;
import com.wen.common.result.PageResult;
import com.wen.common.result.Result;
import com.wen.common.result.ResultCode;
import com.wen.common.result.ResultService;
import com.wen.common.constant.UserConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import com.wen.user.service.UserService;

/**
 * 控制器层
 *
 * @author Administrator
 */
@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;


	/**
	 * 查询全部数据
	 *
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public Result findAll() {
		return Result.ok(ResultCode.SUCCESS, userService.findAll());
	}

	/**
	 * 根据ID查询
	 *
	 * @param id ID
	 * @return
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public Result findById(@PathVariable String id) {
		return Result.ok(ResultCode.SUCCESS, userService.findById(id));
	}


	/**
	 * 分页+多条件查询
	 *
	 * @param searchMap 查询条件封装
	 * @param page      页码
	 * @param size      页大小
	 * @return 分页结果
	 */
	@RequestMapping(value = "/search/{page}/{size}", method = RequestMethod.POST)
	public Result findSearch(@RequestBody Map searchMap, @PathVariable int page, @PathVariable int size) {
		Page<User> pageList = userService.findSearch(searchMap, page, size);
		return Result.ok(ResultCode.SUCCESS, new PageResult<User>(pageList.getTotalElements(), pageList.getContent()));
	}

	/**
	 * 根据条件查询
	 *
	 * @param searchMap
	 * @return
	 */
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public Result findSearch(@RequestBody Map searchMap) {
		return Result.ok(ResultCode.SUCCESS, userService.findSearch(searchMap));
	}

	/**
	 * 增加
	 *
	 * @param user
	 */
	@RequestMapping(method = RequestMethod.POST)
	public Result add(@RequestBody User user) {
		userService.add(user);
		return Result.ok(ResultCode.SUCCESS, null);
	}

	/**
	 * 修改
	 *
	 * @param user
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public Result update(@RequestBody User user, @PathVariable String id) {
		user.setId(id);
		userService.update(user);
		return Result.ok(ResultCode.SUCCESS, null);
	}

	/**
	 * 删除
	 *
	 * @param id
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public Result delete(@PathVariable String id) {
		userService.deleteById(id);
		return Result.ok(ResultCode.SUCCESS, null);
	}

	/**
	 *  发送短信验证码
	 */
	@PostMapping("/sendsms/{mobile}")
	public Result sendSms(@PathVariable("mobile") String mobile){
		ResultService<Void> service = userService.sendSms(mobile);
		if (service.isB()) {
			return Result.ok(ResultCode.SUCCESS, null);
		}
		return Result.ok(ResultCode.FAIL, null);
	}

	/**
	 *  用户注册
	 */
	@PostMapping("/register/{code}")
	public Result register(@PathVariable("code") String code,
						   @RequestBody User user){
		if (user == null || user.getMobile() == null) {
			return Result.ok(ResultCode.FAIL, null);
		}
		ResultService<Void> service = userService.register(code, user);
		if (service.isB()) {
			return Result.ok(ResultCode.SUCCESS, null);
		}
		return Result.ok(ResultCode.FAIL, null);
	}

	/**
	 *  登录
	 */
	@PostMapping("/login")
	public Result login(@RequestBody User user){
		if (user == null || user.getMobile() == null || user.getPassword() == null) {
			return Result.ok(ResultCode.FAIL, null);
		}
		ResultService<String> service = userService.login(user);
		if (service.isB()) {
			// 取出jwtToken
			String jwtToken = service.getRows();
			Map<String, Object> map = new HashMap<>();
			map.put("token", jwtToken);
			map.put("role", UserConstant.user);
			return Result.ok(ResultCode.SUCCESS, map);
		}
		return Result.ok(ResultCode.FAIL, null);
	}

	/**
	 *  更新粉丝和关注数
	 */
	@PutMapping("/{userId}/{friendId}/{num}")
	public void updateFanscountFollowcount(@PathVariable("userId") String userId,
										   @PathVariable("friendId") String friendId,
										   @PathVariable("num") Integer num){
		 userService.updateFanscountFollowcount(userId, friendId, num);
	}

}
