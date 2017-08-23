/**
 * 
 */
package com.jt.web.controller;

import java.util.List;
import org.aspectj.lang.reflect.CatchClauseSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.common.vo.SysResult;
import com.jt.web.pojo.Cart;
import com.jt.web.service.CartService;
import com.jt.web.threadLocal.UserThreadLocal;

/**
 * @author Airey
 * @date   2017年8月9日
 */
@Controller
@RequestMapping("/cart")
public class CartController {
	@Autowired
	private CartService cartService;

	// 展现我的购物车
	@RequestMapping("/show")
	public String myCart(Model model) throws Exception {
		// 准备数据
		// Long userId = 7L;
		Long userId = UserThreadLocal.getUserId();
		List<Cart> cartList = cartService.queryByUserId(userId);
		model.addAttribute("cartList", cartList);
		return "cart";
	}

	// 新增商品到购物车 cart/add/${item.id}.html
	@RequestMapping("/add/{itemId}")
	public String addCart(Cart cart) throws Exception {
		Long userId = UserThreadLocal.getUserId();
		cart.setUserId(userId);
		cartService.saveCart(cart);

		return "redirect:/cart/show.html";// 跟浏览器的url一致
	}

	// 更新商品数量，重新设置 /service/cart/update/num/562379/4
	@RequestMapping("/update/num/{itemId}/{num}")
	@ResponseBody
	public String updateNum(Cart cart) throws Exception {
		Long userId = UserThreadLocal.getUserId();
		cart.setUserId(userId);
		cartService.updateNum(cart);
		return ""; // 页面如果不设置返回值，业务操作正常，但是回调时js错误，给一个空值，它结构正确不报错
	}

	// 删除某个用户的某个商品
	@RequestMapping("/delete/{itemId}")
	public String deleteCart(Cart cart) throws Exception {

		Long userId = UserThreadLocal.getUserId();
		cart.setUserId(userId);

		cartService.deleteCart(cart);
		return "redirect:/cart/show.html";
	}
}
