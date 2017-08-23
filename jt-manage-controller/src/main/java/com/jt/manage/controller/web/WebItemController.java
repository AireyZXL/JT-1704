/**
 * 
 */
package com.jt.manage.controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.manage.pojo.Item;
import com.jt.manage.pojo.ItemDesc;
import com.jt.manage.service.ItemService;

/**
 * @author Airey
 * @date   2017年8月4日
 */
@Controller
public class WebItemController {
	@Autowired
	private ItemService itemService;

	// 根据商品id查询某个商品
	@RequestMapping("/web/item/{itemId}")
	@ResponseBody
	public Item getItemById(@PathVariable Long itemId) {
		return itemService.queryById(itemId);
	}

	// 根据商品id查询某个商品描述
	@RequestMapping("/web/item/desc/{itemId}")
	@ResponseBody
	public ItemDesc getItemDescById(@PathVariable Long itemId) {
		return itemService.getItemDescById(itemId);
	}
}
