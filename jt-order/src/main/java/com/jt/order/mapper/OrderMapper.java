/**
 * 
 */
package com.jt.order.mapper;

import java.util.Date;

import com.jt.common.mapper.SysMapper;
import com.jt.order.pojo.Orders;

/**
 * @author Airey
 * @date   2017年8月11日
 */
public interface OrderMapper extends SysMapper<Orders>{

	/**
	 * @param orders
	 */
	void create(Orders orders);

	/**
	 * @param orderId
	 * @return
	 */
	Orders queryByOrderId(String orderId);

	/**
	 * 
	 * 配合未支付订单定时任务
	 * @param date
	 */
	void paymentOrderScan(Date date);
	
	

}
