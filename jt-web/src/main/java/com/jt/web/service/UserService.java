/**
 * 
 */
package com.jt.web.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.service.BaseService;
import com.jt.common.service.HttpClientService;
import com.jt.common.vo.SysResult;
import com.jt.web.pojo.User;

/**
 * @author Airey
 * @date   2017年8月7日
 */
@Service
public class UserService {
	@Autowired
	private HttpClientService httpClientService;
	private static final ObjectMapper MAPPER = new ObjectMapper();

	/**
	 * 注册
	 * @param user
	 * @return
	 * @throws Exception 
	 */
	public SysResult doRegister(User user) throws Exception {
		// 利用httpClient请求
		String url = "http://sso.jt.com/user/register";
		// 封装httpClient,它的参数必须用map封装,然后参数都是字符串
		Map<String, String> params = new HashMap<String, String>();
		params.put("username", user.getUsername());
		params.put("password", user.getPassword());
		params.put("phone", user.getPhone());
		params.put("email", user.getEmail());

		String jsonData = httpClientService.doPost(url, params);
		// 把数据取出来，然后重新放进去。SysResult这个对象写的不是很标准，Jackson不能正确转换
		JsonNode jsonNode = MAPPER.readTree(jsonData);
		String username = jsonNode.get("data").asText();
		return SysResult.oK(username);
	}

	/**
	 * @param user
	 * @return
	 * @throws Exception 
	 */
	public String login(User user) throws Exception {
		System.out.println(1111111111);
		String url = "http://sso.jt.com/user/login";
		Map<String, String> params = new HashMap<String, String>();
		params.put("u", user.getUsername());
		params.put("p", user.getPassword());
		String jsonData = httpClientService.doPost(url, params);//SysResult
		JsonNode jsonNode = MAPPER.readTree(jsonData);
		String ticket = jsonNode.get("data").asText();
		return ticket;
	}

}
