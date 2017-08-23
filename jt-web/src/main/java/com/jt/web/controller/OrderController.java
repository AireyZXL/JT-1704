/**
 * 
 */
package com.jt.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.common.vo.SysResult;
import com.jt.web.pojo.Cart;
import com.jt.web.pojo.Orders;
import com.jt.web.service.OrderService;
import com.jt.web.threadLocal.UserThreadLocal;

/**
 * @author Airey
 * @date   2017年8月11日
 */
@Controller
@RequestMapping("/order")
public class OrderController {
	@Autowired
	private OrderService orderService;

	// 转向订单页面
	@RequestMapping("/create")
	public String orderCreate(Model model) throws Exception {
		// 页面要准备购物车的信息carts（获取当前用户的购物车的所有数据）
		//Long userId = 7L;
		Long userId=UserThreadLocal.getUserId();
		List<Cart> carts = orderService.queryByUserId(userId);
		model.addAttribute("carts", carts);

		return "order-cart";
	}

	// 提交订单 /service/order/submit
	@RequestMapping("/submit")
	@ResponseBody
	public SysResult orderSubmit(Orders orders) throws Exception {
		//Long userId = 7L;
		Long userId=UserThreadLocal.getUserId();
		orders.setUserId(userId);
		String orderId = orderService.orderSubmit(orders);
		return SysResult.oK(orderId);
	}

	// 转向成功提示页面/order/success.html?id=123
	@RequestMapping("/success")
	public String success(String id, Model model) throws Exception {
		// 准备数据，根据orderId查询orders
		Orders orders = orderService.queryByOrderId(id);
		model.addAttribute("order", orders);
		return "success";

	}

}
