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
import com.jt.web.threadLocal.UserThreadLocal;

/**
 * @author Airey
 * @date   2017年8月14日
 */
public class OrderInterceptor implements HandlerInterceptor {
	@Autowired
	private HttpClientService httpClientService;
	private static final ObjectMapper MAPPER = new ObjectMapper();

	// 在controller执行业务方法之前执行
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		/**
		 * 1.从cookie中获取ticket
		 * 2.根据ticket调用SSO提供的业务接口去redis中获取
		 * 3.如果都存在就获取到user.json，转换成javaUser对象，放入UserThreadLocal
		 * 4.如果cookie/redis有一个不存在，就转向登录界面
		 * 5.执行完，一定要放行，在controller中就可以调用UserThreadLocal.userId
		 * 
		 */
		String ticket = CookieUtils.getCookieValue(request, UserController.JT_TICKET);
		if (StringUtils.isNotEmpty(ticket)) {
			String url = "http://sso.jt.com/user/query/" + ticket;
			String jsonData = httpClientService.doGet(url);

			if (StringUtils.isNotEmpty(jsonData)) {
				JsonNode jsonNode = MAPPER.readTree(jsonData);
				String userJson = jsonNode.get("data").asText();
				User cUser = MAPPER.readValue(userJson, User.class);
				UserThreadLocal.set(cUser);
				return true;
			} else {
				UserThreadLocal.set(null);
			}
		} else {
			UserThreadLocal.set(null);
		}

		// cookie不存在，redis值不存在，重定向登录页面
		response.sendRedirect("/user/login.html");

		return false;// false坑，不放行，true放行
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub

	}

}
