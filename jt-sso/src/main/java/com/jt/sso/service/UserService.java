/**
 * 
 */
package com.jt.sso.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.print.DocFlavor.STRING;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.service.BaseService;
import com.jt.common.service.RedisService;
import com.jt.common.vo.SysResult;
import com.jt.sso.mapper.UserMapper;
import com.jt.sso.pojo.User;

/**
 * @author Airey
 * @date   2017年8月6日
 */
@Service
public class UserService extends BaseService<User> {

	private static final Map<Integer, String> PARAM_TYPE = new HashMap<Integer, String>();
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private RedisService redisService;
	private static final String TICKET = "TICKET";
	private static final Integer TICKET_TIME = 60 * 60;// 一小时
	private static final ObjectMapper MAPPER = new ObjectMapper();

	static {
		PARAM_TYPE.put(1, "username");
		PARAM_TYPE.put(2, "phone");
		PARAM_TYPE.put(3, "email");
	}

	/**
	 * 校验参数是否合法
	 * @param param
	 * @param type
	 * @return
	 */
	public Boolean check(String param, Integer type) {
		// 校验数据是否合法
		if (!PARAM_TYPE.containsKey(type)) {
			return false;
		}
		// 从数据库查询
		Integer count = userMapper.check(param, PARAM_TYPE.get(type));
		if (count > 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @param user
	 * @return
	 */
	public String register(User user) {
		user.setCreated(new Date());
		user.setUpdated(user.getCreated());
		if (StringUtils.isEmpty(user.getEmail())) {
			// js加判断，如果email是以---符号开头，不展示
			user.setEmail("---" + user.getPhone());
		}
		user.setPassword(DigestUtils.md5Hex(user.getPassword()));
		super.saveSelective(user);
		return user.getUsername();
	}

	/**
	 * @param u
	 * @param p
	 * @return
	 */
	public String login(String username, String password) {
		User param = new User();
		param.setUsername(username);
		// 1.根据用户名查询，返回当前用户对象
		User cUser = super.queryByWhere(param);
		if (cUser != null) {
			// 2.密码进行比较
			password = DigestUtils.md5Hex(password);// 加密
			if (password.equals(cUser.getPassword())) {
				try {
					// 3.产生令牌：唯一性、动态性、混淆性，提升安全性！
					String ticket = DigestUtils
							.md5Hex("TICKET_" + username + cUser.getId() + System.currentTimeMillis());
					// 4.写redis
					String userJson = MAPPER.writeValueAsString(cUser);
					// 设置redis过期时间，一般电商用户过期时间为7天或者10天，就按下面的形式写，在编译时，编译器会自动优化，写成结果集
					redisService.set(ticket, userJson, 60 * 60 * 24 * 7);
					//redisService.set(ticket, userJson);
					return ticket;
				} catch (JsonProcessingException e) {
					e.printStackTrace();
				}
			}
		}

		return null;
	}

}
