package com.wen.user.service;

import java.util.*;
import java.util.concurrent.TimeUnit;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wen.common.exception.TenException;
import com.wen.common.model.User;
import com.wen.common.result.ResultCode;
import com.wen.common.result.ResultService;
import com.wen.common.utils.IdWorker;
import com.wen.common.utils.JwtUtil;
import com.wen.common.constant.UserConstant;
import com.wen.user.interceptor.JwtInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.wen.user.dao.UserDao;
import org.springframework.transaction.annotation.Transactional;


/**
 * 服务层
 * 
 * @author Administrator
 *
 */
@Service
@Slf4j
public class UserService {

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private IdWorker idWorker;

	/**
	 * 查询全部列表
	 * @return
	 */
	public List<User> findAll() {
		return userDao.findAll();
	}

	
	/**
	 * 条件查询+分页
	 * @param whereMap
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<User> findSearch(Map whereMap, int page, int size) {
		Specification<User> specification = createSpecification(whereMap);
		PageRequest pageRequest =  PageRequest.of(page-1, size);
		return userDao.findAll(specification, pageRequest);
	}

	
	/**
	 * 条件查询
	 * @param whereMap
	 * @return
	 */
	public List<User> findSearch(Map whereMap) {
		Specification<User> specification = createSpecification(whereMap);
		return userDao.findAll(specification);
	}

	/**
	 * 根据ID查询实体
	 * @param id
	 * @return
	 */
	public User findById(String id) {
		return userDao.findById(id).get();
	}

	/**
	 * 增加
	 * @param user
	 */
	public void add(User user) {
		user.setId( idWorker.nextId()+"" );
		userDao.save(user);
	}

	/**
	 * 修改
	 * @param user
	 */
	public void update(User user) {
		userDao.save(user);
	}

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private HttpServletRequest request;

	/**
	 * 删除
	 * @param id
	 */
	public void deleteById(String id) {
		User userInfo = JwtInterceptor.getUserInfo();
		String role = userInfo.getRole();
		if (role == null || !role.equals(UserConstant.admin) || role.equals("")){
			// 权限不足
			throw new TenException(ResultCode.INSUFFICIENTPERMISSIONS);
		}
		userDao.deleteById(id);
	}

	/**
	 * 动态条件构建
	 * @param searchMap
	 * @return
	 */
	private Specification<User> createSpecification(Map searchMap) {

		return new Specification<User>() {

			@Override
			public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicateList = new ArrayList<Predicate>();
                // ID
                if (searchMap.get("id")!=null && !"".equals(searchMap.get("id"))) {
                	predicateList.add(cb.like(root.get("id").as(String.class), "%"+(String)searchMap.get("id")+"%"));
                }
                // 手机号码
                if (searchMap.get("mobile")!=null && !"".equals(searchMap.get("mobile"))) {
                	predicateList.add(cb.like(root.get("mobile").as(String.class), "%"+(String)searchMap.get("mobile")+"%"));
                }
                // 密码
                if (searchMap.get("password")!=null && !"".equals(searchMap.get("password"))) {
                	predicateList.add(cb.like(root.get("password").as(String.class), "%"+(String)searchMap.get("password")+"%"));
                }
                // 昵称
                if (searchMap.get("nickname")!=null && !"".equals(searchMap.get("nickname"))) {
                	predicateList.add(cb.like(root.get("nickname").as(String.class), "%"+(String)searchMap.get("nickname")+"%"));
                }
                // 性别
                if (searchMap.get("sex")!=null && !"".equals(searchMap.get("sex"))) {
                	predicateList.add(cb.like(root.get("sex").as(String.class), "%"+(String)searchMap.get("sex")+"%"));
                }
                // 头像
                if (searchMap.get("avatar")!=null && !"".equals(searchMap.get("avatar"))) {
                	predicateList.add(cb.like(root.get("avatar").as(String.class), "%"+(String)searchMap.get("avatar")+"%"));
                }
                // E-Mail
                if (searchMap.get("email")!=null && !"".equals(searchMap.get("email"))) {
                	predicateList.add(cb.like(root.get("email").as(String.class), "%"+(String)searchMap.get("email")+"%"));
                }
                // 兴趣
                if (searchMap.get("interest")!=null && !"".equals(searchMap.get("interest"))) {
                	predicateList.add(cb.like(root.get("interest").as(String.class), "%"+(String)searchMap.get("interest")+"%"));
                }
                // 个性
                if (searchMap.get("personality")!=null && !"".equals(searchMap.get("personality"))) {
                	predicateList.add(cb.like(root.get("personality").as(String.class), "%"+(String)searchMap.get("personality")+"%"));
                }
				
				return cb.and( predicateList.toArray(new Predicate[predicateList.size()]));

			}
		};

	}

	@Autowired
	private StringRedisTemplate redisTemplate;
	@Autowired
	private RabbitTemplate rabbitTemplate;
	private static final String check_code_prefix = "check_code_";
	@Autowired
	private ObjectMapper objectMapper;
	public ResultService<Void> sendSms(String mobile) {

		String code = RandomStringUtils.randomNumeric(6);
		// 存到缓存
		String key = check_code_prefix + mobile;
		// 缓存10分钟
		redisTemplate.opsForValue().set(key, code, UserConstant.redisExepire, TimeUnit.MINUTES);

		Map<String, String> map = new HashMap<>();
		map.put("mobile", mobile);
		map.put("code", code);
		log.info("给手机号为: {} 的用户发送验证码 code: {}", mobile, code);
		try {
			// 发送给sms服务,发短信
			rabbitTemplate.convertAndSend(UserConstant.exchange, UserConstant.routingKey, objectMapper.writeValueAsString(map));
		} catch (Exception e) {
			log.error("发送验证码失败");
			e.printStackTrace();
		}
		return new ResultService<>(true);
	}

	@Autowired
	private BCryptPasswordEncoder encoder;

	public ResultService<Void> register(String code, User user) {
		String key = check_code_prefix + user.getMobile();
		// 从redis获取缓存
		String cacheCode = redisTemplate.opsForValue().get(key);
		// 缓存过期
		if (cacheCode == null) {
			return new ResultService<>(false);
		}
		// 验证码错误
		if (!cacheCode.equals(code)) {
			return new ResultService<>(false);
		}

		// 处理注册业务
		// 密码加密
		user.setPassword(encoder.encode(user.getPassword()));
		// 粉丝数
		user.setFanscount(0);
		// 关注数
		user.setFollowcount(0);
		// 在线时长
		user.setOnline(0L);
		// 注册日期
		user.setRegdate(new Date());
		// 修改日期
		user.setUpdatedate(new Date());
		// 最后登录日期
		user.setLastdate(new Date());
		userDao.save(user);
		return new ResultService<>(true);
	}

	public ResultService<String> login(User user) {
		// 根据用户名查询
		User dbUser = userDao.findByMobile(user.getMobile());
		if (dbUser == null) {
			return new ResultService<>(false);
		}
		// 判断密码是否一致
		if (encoder.matches(dbUser.getPassword(), dbUser.getPassword())) {
			// 生成jwtToken
			String jwtToken = jwtUtil.createJWT(dbUser.getId(), dbUser.getNickname(), UserConstant.user);
			return new ResultService<>(true, 1, jwtToken);
		}

		return new ResultService<>(false);
	}

	@Transactional
	public void updateFanscountFollowcount(String userId, String friendId, Integer num) {
		// 自己的关注数 +1
		userDao.updateFanscount(num, friendId);
		// 被关注的粉丝数 +1
		userDao.updateFollowcount(num, userId);
	}
}
