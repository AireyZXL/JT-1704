/**
 * 
 */
package com.jt.cart.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.cart.pojo.Cart;
import com.jt.cart.service.CartService;
import com.jt.common.vo.SysResult;

/**
 * @author Airey
 * @date   2017年8月8日
 */
@Controller
@RequestMapping("cart")
public class CartController {
	@Autowired
	private CartService cartService;

	// 我的购物车
	@RequestMapping("/query/{userId}")
	@ResponseBody
	public SysResult queryByUserId(@PathVariable Long userId) {
		List<Cart> cartList = cartService.queryByUserId(userId);
		return SysResult.oK(cartList);
	}

	// 新增商品到购物车
	@RequestMapping("/save")
	@ResponseBody
	public SysResult saveCart(Cart cart) {
		cartService.saveCart(cart);
		return SysResult.oK();
	}

	// 更新商品数量
	@RequestMapping("/update/num/{userId}/{itemId}/{num}")
	@ResponseBody
	public SysResult updateNum(Cart cart) {
		cartService.updateNum(cart);
		return SysResult.oK();
	}

	// 删除某个用户的某个商品
	@RequestMapping("/delete/{userId}/{itemId}")
	@ResponseBody
	public SysResult deleteCart(Cart cart) {
		cartService.deleteCart(cart);
		return SysResult.oK();
	}

}
