/**
 * 
 */
package com.jt.cart.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jt.cart.mapper.CartMapper;
import com.jt.cart.pojo.Cart;
import com.jt.common.service.BaseService;

/**
 * @author Airey
 * @date   2017年8月8日
 */
@Service
public class CartService extends BaseService<Cart> {
	@Autowired
	private CartMapper cartMapper;

	// 我的购物车(某人的购物车)
	public List<Cart> queryByUserId(Long userId) {
		Cart param = new Cart();
		param.setUserId(userId);
		// 利用pojo对象来传递where条件参数，要求:属性不为null的才拼接到where
		List<Cart> cartList = cartMapper.select(param);
		return cartList;
	}

	/**
	 * 新增商品到购物车中
	 */
	public void saveCart(Cart cart) {
		cart.setCreated(new Date());
		cart.setUpdated(cart.getCreated());
		/*
		 * 判断此用户的此商品是否成功，存在直接累加数量=旧的商品数量+新的商品数量 不存在，直接存入数据库
		 */
		Cart param = new Cart();
		param.setUserId(cart.getUserId());
		param.setItemId(cart.getItemId());

		Cart curCart = super.queryByWhere(param);
		if (curCart == null) {
			cartMapper.insertSelective(cart);
		} else {
			curCart.setNum(curCart.getNum() + cart.getNum());
			cartMapper.updateByPrimaryKey(curCart);
		}
	}

	/**
	 * @param cart
	 */
	public void updateNum(Cart cart) {
        cartMapper.updateNum(cart);		
	}

	/**
	 * @param cart
	 */
	public void deleteCart(Cart cart) {
        cartMapper.delete(cart);		
	}

}
