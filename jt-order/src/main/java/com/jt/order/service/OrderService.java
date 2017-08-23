/**
 * 
 */
package com.jt.order.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jt.common.service.BaseService;
import com.jt.order.mapper.OrderMapper;
import com.jt.order.pojo.Orders;

/**
 * @author Airey
 * @date   2017年8月11日
 */
@Service
public class OrderService extends BaseService<Orders> {
	@Autowired
	private OrderMapper orderMapper;

	// 创建订单
	public String create(Orders orders) {
		// 设置订单id：规则：userId+当前的毫秒数
		String orderId = orders.getUserId() + "" + System.currentTimeMillis();
		orders.setOrderId(orderId);

		orderMapper.create(orders);
		return orderId;
	}

	// 按订单号查询
	public Orders queryByOrderId(String orderId) {
		Orders orders = orderMapper.queryByOrderId(orderId);
		return orders;
	}

}
