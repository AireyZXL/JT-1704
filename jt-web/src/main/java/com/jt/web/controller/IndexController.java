/**
 * 
 */
package com.jt.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jt.common.service.PropertieService;
import com.jt.web.service.PropertyService;

/**
 * @author Airey
 * @date   2017年8月3日
 */
@Controller
public class IndexController {
	@Autowired
	private PropertyService propertieService;
	//转向首页
		@RequestMapping("index")
		public String index(){
			System.out.println(propertieService.MANAGE_URL);
			return "index";
		}
		
    
}
