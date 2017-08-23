/**
 * 
 */
package com.jt.manage.controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.manage.service.ItemCatService;
import com.jt.manage.service.ItemService;

/**
 * @author Airey
 * @date   2017年8月3日
 */
@Controller
public class WebItemCatController {
	@Autowired
	private ItemCatService itemCatService;

	// 准备数据
	// GET : http://manage.jt.com/web/itemcat/all?callback=category.getDataService
	@RequestMapping("/web/itemcat/all")
	@ResponseBody // 将java对象自动json串,jackson
	public Object getItemCat(String callback) {
		// springmvc提供专门的对象来支持jsonp，参数就是原来返回java对象
		MappingJacksonValue mjv = new MappingJacksonValue(itemCatService.getItemCat());
		mjv.setJsonpFunction(callback);
		return mjv;
	}
}
