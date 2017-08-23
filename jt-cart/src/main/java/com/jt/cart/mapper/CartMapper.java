/**
 * 
 */
package com.jt.cart.mapper;

import com.jt.cart.pojo.Cart;
import com.jt.common.mapper.SysMapper;

/**
 * @author Airey
 * @date   2017年8月8日
 */
public interface CartMapper extends SysMapper<Cart> {

	/**
	 * @param cart
	 */
	void updateNum(Cart cart);

}
