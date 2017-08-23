/**
 * 
 */
package com.jt.order.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.vo.SysResult;
import com.jt.order.pojo.Orders;
import com.jt.order.service.OrderService;

/**
 * @author Airey
 * @date   2017年8月11日
 */
@Controller
@RequestMapping("order")
public class OrderController {
	@Autowired
	private OrderService orderService;
	private static final ObjectMapper MAPPER = new ObjectMapper();

	// 创建订单
	@RequestMapping("/create")
	@ResponseBody
	// 请求的提交以json提交，获取到json，spirngmvc封装到一个字符串
	public SysResult create(@RequestBody String json) throws JsonParseException, JsonMappingException, IOException {
		// 将页面提交的json格式数据转成java对象
		Orders orders = MAPPER.readValue(json, Orders.class);
		String orderId = orderService.create(orders);
		return SysResult.oK(orderId);
	}

	// 查询订单
	@RequestMapping("/query/{orderId}")
	@ResponseBody
	public Orders queryByOrderId(@PathVariable String orderId) {
		return orderService.queryByOrderId(orderId);
	}
}
