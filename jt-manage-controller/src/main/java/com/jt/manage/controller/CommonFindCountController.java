/**
 * 
 */
package com.jt.manage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.manage.service.ItemService;

/**
 * @author Airey
 * @date   2017年7月24日
 */
@Controller
public class CommonFindCountController {
   @Autowired
   private ItemService itemService;
   @RequestMapping("/findCount")
   @ResponseBody
   public int findCount(){
	   return itemService.findCount();
   }
}
