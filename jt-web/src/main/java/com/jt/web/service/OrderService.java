/**
 * 
 */
package com.jt.web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.service.HttpClientService;
import com.jt.web.pojo.Cart;
import com.jt.web.pojo.Orders;

/**
 * @author Airey
 * @date   2017年8月11日
 */
@Service
public class OrderService {

	@Autowired
	private HttpClientService httpClientService;
	private static final ObjectMapper MAPPER = new ObjectMapper();

	/**
	 * 获取我的购物车数据
	 * @param userId
	 * @return
	 * @throws Exception 
	 */
	public List<Cart> queryByUserId(Long userId) throws Exception {
		String url = "http://cart.jt.com/cart/query/" + userId;
		String jsonDate = httpClientService.doGet(url);
		JsonNode jsonNode = MAPPER.readTree(jsonDate);
		JsonNode data = jsonNode.get("data");
		Object obj = null;
		if (data.isArray() && data.size() > 0) {
			obj = MAPPER.readValue(data.traverse(),
					MAPPER.getTypeFactory().constructCollectionType(List.class, Cart.class));
		}

		return (List<Cart>) obj;
	}

	/**
	 * 订单提交
	 * @param orders
	 * @return
	 * @throws Exception 
	 */
	public String orderSubmit(Orders orders) throws Exception {
		// 前台系统就要访问订单业务接口generated method stub
		String url = "http://order.jt.com/order/create";
		String json = MAPPER.writeValueAsString(orders);
		String jsonData = httpClientService.doPostJson(url, json);
		JsonNode jsonNode = MAPPER.readTree(jsonData);
		String orderId = jsonNode.get("data").asText();
		return orderId;
	}

	/**
	 * 根据orderId查询orders
	 * @param id
	 * @return
	 * @throws Exception 
	 */
	public Orders queryByOrderId(String orderId) throws Exception {

		String url = "http://order.jt.com/order/query/" + orderId;
		String jsonData = httpClientService.doGet(url);

		return MAPPER.readValue(jsonData, Orders.class);
	}

}
