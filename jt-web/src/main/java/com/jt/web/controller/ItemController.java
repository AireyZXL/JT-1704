/**
 * 
 */
package com.jt.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jt.web.pojo.Item;
import com.jt.web.pojo.ItemDesc;
import com.jt.web.service.ItemService;

/**
 * @author Airey
 * @date   2017年8月3日
 */
@Controller
public class ItemController {
	@Autowired
	private ItemService itemService;

	// 获取商品信息转向item.jsp页面 /items/562379.html
	@RequestMapping("/items/{itemId}")
	public String item(@PathVariable Long itemId, Model model) {
		// 获取商品信息
		// TODO 图片显示
		Item item = itemService.getItemById(itemId);
		model.addAttribute("item", item);
		// 获取商品描述
		ItemDesc itemDesc = itemService.getItemDescByItemId(itemId);
		model.addAttribute("itemDesc", itemDesc);

		return "item";
	}
}
