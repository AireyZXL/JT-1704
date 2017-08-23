/**
 * 
 */
package com.jt.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.service.HttpClientService;
import com.jt.common.util.CookieUtils;
import com.jt.web.controller.UserController;
import com.jt.web.pojo.User;
import com.jt.web.service.CartService;
import com.jt.web.service.UserService;
import com.jt.web.threadLocal.UserThreadLocal;

/**
 * @author Airey
 * @date   2017年8月10日
 */
//springMVC拦截器
public class CartInterceptor implements HandlerInterceptor {

	@Autowired
	private HttpClientService httpClientService;
	private static final ObjectMapper MAPPPER = new ObjectMapper();

	// 处理controller方法之前调用
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// 获取userId
		/*
		 * 步骤： 1.读取cookie 2.调用sso业务接口去获取redis中的值 3.UserThreadLocal.user
		 * 4.判断如果没有cookie,redis直接跳转登录界面
		 * 
		 */
		String ticket = CookieUtils.getCookieValue(request, UserController.JT_TICKET);
		if (StringUtils.isNotEmpty(ticket)) {
			String url = "http://sso.jt.com/user/query/" + ticket;
			String jsonData = httpClientService.doGet(url);
			if (StringUtils.isNotEmpty(jsonData)) {
				// 解析出user.json
				JsonNode jsonNode = MAPPPER.readTree(jsonData);
				String userJson = jsonNode.get("data").asText();
				User user = MAPPPER.readValue(userJson, User.class);
				UserThreadLocal.set(user);
				return true;
			} else {
				UserThreadLocal.set(null);
			}
		} else {
			UserThreadLocal.set(null);
		}
		// cookie不存在，redis值不存在，重定向登录页面
		response.sendRedirect("/user/login.html");
		return false; // false坑，不放行，true放行
	}

	// 处理controller方法之后调用
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub

	}

	// 渲染renderViewResolver之后调用
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub

	}

}
