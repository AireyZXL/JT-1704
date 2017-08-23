/**
 * 
 */
package com.jt.web.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.client.HttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.service.HttpClientService;
import com.jt.web.pojo.Cart;

/**
 * @author Airey
 * @date   2017年8月9日
 */
@Service
public class CartService {
	@Autowired
	private HttpClientService httpClientService;
	private static final ObjectMapper MAPPER = new ObjectMapper();

	// 根据userId来查询我的购物车数据，多次转换
	public List<Cart> queryByUserId(Long userId) throws Exception {
		String url = "http://cart.jt.com/cart/query/" + userId;

		String jsonData = httpClientService.doGet(url);
		JsonNode jsonNode = MAPPER.readTree(jsonData);
		JsonNode cartListNode = jsonNode.get("data");
		Object obj = null;
		if (cartListNode.isArray() && cartListNode.size() > 0) {
			obj = MAPPER.readValue(cartListNode.traverse(),
					MAPPER.getTypeFactory().constructCollectionType(List.class, Cart.class));
		}
		return (List<Cart>) obj;
	}

	/**
	 * @param cart
	 * @throws Exception 
	 */
	public void saveCart(Cart cart) throws Exception {
		String url = "http://cart.jt.com/cart/save";
		Map<String, String> params = new HashMap<String, String>();
		params.put("userId", cart.getUserId() + "");
		params.put("itemId", cart.getItemId() + "");
		params.put("itemTitle", cart.getItemTitle());
		params.put("itemImage", cart.getItemImage());
		params.put("itemPrice", cart.getItemPrice() + "");
		params.put("num", cart.getNum() + "");

		httpClientService.doPost(url, params, "utf-8");

	}

	/**
	 * @param cart
	 * @throws Exception 
	 */
	public void updateNum(Cart cart) throws Exception {
		String url = "http://cart.jt.com/cart/update/num/" + cart.getUserId() + "/" + cart.getItemId() + "/"
				+ cart.getNum();
		httpClientService.doGet(url);
	}

	/**
	 * @param cart
	 * @throws Exception 
	 */
	public void deleteCart(Cart cart) throws Exception {
		String url = "http://cart.jt.com/cart/delete/" + cart.getUserId() + "/" + cart.getItemId();
		httpClientService.doGet(url);
	}
}
