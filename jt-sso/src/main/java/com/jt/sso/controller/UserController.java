/**
 * 
 */
package com.jt.sso.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.common.service.RedisService;
import com.jt.common.vo.SysResult;
import com.jt.sso.pojo.User;
import com.jt.sso.service.UserService;

/**
 * @author Airey
 * @date   2017年8月6日
 */
@Controller
@RequestMapping("user")
public class UserController {
	@Autowired
	private UserService userService;
	@Autowired
	private RedisService redisService;
	// 加入Log4j的日志
	private static final Logger log = Logger.getLogger(UserController.class);

	/**
	* 检测数据是否可用
	* 
	* @param param 用户输入的参数
	* @param type type为类型，可选参数1、2、3分别代表username、phone、email
	* @return
	*/
	@RequestMapping(value = "/check/{param}/{type}", method = RequestMethod.GET)
	@ResponseBody //它就被全局的转换器替代
	public SysResult check(@PathVariable("param") String param, @PathVariable("type") Integer type) {

		try {
			Boolean b = userService.check(param, type);
//			// 改造返回值为jsonp格式
//			MappingJacksonValue mjv = new MappingJacksonValue(SysResult.oK(b));
//			mjv.setJsonpFunction(callback);
			return SysResult.oK(b);
		} catch (Exception e) {
			log.error(e.getMessage());
			return SysResult.build(201, e.getMessage());
		}
	}

	// 注册
	@RequestMapping("/register")
	@ResponseBody
	public SysResult register(User user) {
		try {
			String username = userService.register(user);
			return SysResult.oK(username);
		} catch (Exception e) {
			log.error(e.getMessage());
			return SysResult.build(201, e.getMessage());
		}
	}

	// 登录
	@RequestMapping("/login")
	@ResponseBody
	public SysResult login(String u, String p) {
		String ticket = userService.login(u, p);
		return SysResult.oK(ticket);
	}

	// 根据ticket来查询当前用户信息
	@RequestMapping("/query/{ticket}")
	@ResponseBody
	public SysResult queryByTicket(@PathVariable String ticket) {
		try {
			String userJson = redisService.get(ticket);
			return SysResult.oK(userJson);
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.build(201, e.getMessage());
		}

	}

}
