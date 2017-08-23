/**
 * 
 */
package com.jt.web.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.common.util.CookieUtils;
import com.jt.common.vo.SysResult;
import com.jt.web.pojo.User;
import com.jt.web.service.UserService;

/**
 * @author Airey
 * @date   2017年8月7日
 */
@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;
	// 全局唯一
	public static final String JT_TICKET = "JT_TICKET";

	// 转向注册页面
	@RequestMapping("/register.html")
	public String register() {
		return "register";
	}

	@RequestMapping("/login")
	public String login() {
		return "login";
	}

	@RequestMapping("/doRegister")
	@ResponseBody
	public SysResult doRegister(User user) throws Exception {
		return userService.doRegister(user);
	}

	// 登录
	@RequestMapping("/doLogin")
	@ResponseBody
	public SysResult doLogin(User user, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String ticket = userService.login(user);
		// 写入cookie，k(固定死值，本地浏览器)v(ticket)
		CookieUtils.setCookie(request, response, JT_TICKET, ticket);

		return SysResult.oK(ticket);
	}

	// 登出
	@RequestMapping("/logout")
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		// 删除Cookie
		CookieUtils.deleteCookie(request, response, JT_TICKET);
		return "index";
	}

}
