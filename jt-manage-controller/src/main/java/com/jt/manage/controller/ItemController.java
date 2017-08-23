/**
 * 
 */
package com.jt.manage.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.common.vo.EasyUIResult;
import com.jt.common.vo.SysResult;
import com.jt.manage.pojo.Item;
import com.jt.manage.pojo.ItemDesc;
import com.jt.manage.service.ItemDescService;
import com.jt.manage.service.ItemService;

/**
 * @author Airey
 * @date 2017年7月22日
 */
@Controller
@RequestMapping("/item")
public class ItemController {
	// 引入日志文件
	private static final Logger logger = Logger.getLogger(ItemController.class);

	@Autowired
	private ItemService itemService;
	

	/*
	 * easyUI 的全部请求都是AJAX提交 值的传递是以json形式进行的 分页的参数
	 * http://localhost:8091/item/query?page=1&rows=20 page 第几页 rows 显示的行数
	 * {"title":2000,"rows":[{},{},{}]} easyUI的格式要求
	 */

	@RequestMapping("/query")
	@ResponseBody // 将返回值直接转化为JSON串
	public EasyUIResult findItemList(int page, int rows) {

		return itemService.findItemList(page, rows);
	}

	/*
	 * @RequestMapping("/测试") public void findItemList(HttpServletResponse
	 * response) { // itemService.findItemList()---->json try {
	 * response.getWriter().write("dsfsdfa"); } catch (IOException e) {
	 * e.printStackTrace(); } }
	 */

	/*
	 * //根据商品分类ID查询商品分类名称
	 * 
	 * @RequestMapping("/queryItemName") public void queryItemCatName(Long
	 * itemCatId,HttpServletResponse response){
	 * //System.out.println("itemCatId:"+itemCatId); String
	 * name=itemService.findItemCatName(itemCatId); //System.out.println(name);
	 * response.setContentType("text/html;charset=UTF-8"); try {
	 * response.getWriter().write(name); } catch (IOException e) {
	 * e.printStackTrace(); } }
	 */

	@RequestMapping(value = "/queryItemName", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String queryItemCatName(Long itemCatId) {
		String name = itemService.findItemCatName(itemCatId);
		return name;
	}

	/**
	 * 
	 * @param item
	 * @param desc 
	 * @return
	 */
	@RequestMapping("/save")
	@ResponseBody
	public SysResult saveItem(Item item, String desc) {
		try {
			itemService.saveItem(item, desc);
			return SysResult.build(200, "商品新增成功");
		} catch (Exception e) {
			e.printStackTrace();
			// 生产日志消息
			logger.error("~~~~~~~新增商品失败" + e.getMessage());
			return SysResult.build(201, "新增商品失败!请联系管理员");
		}
	}

	@RequestMapping("/update")
	@ResponseBody
	public SysResult updateItem(Item item,String desc) {
		try {
			itemService.updateItem(item,desc);
			return SysResult.build(200, "修改商品成功");
		} catch (Exception e) {
			e.printStackTrace();
			// 生成日志消息
			logger.error("~~~~~~~修改商品失败" + e.getMessage());
			return SysResult.build(201, "修改商品失败!请联系管理员");
		}
	}

	@RequestMapping("/delete")
	@ResponseBody
	public SysResult deleteItem(Long[] ids) {
		try {
			itemService.deleteItem(ids);
			return SysResult.build(200, "商品删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("~~~~~~~删除商品失败" + e.getMessage());
			return SysResult.build(201, "修改商品失败!请联系管理员");
		}
	}

	@RequestMapping("/instock")
	@ResponseBody
	public SysResult instockItem(Long[] ids) {
		try {
			itemService.instockItem(ids);
			return SysResult.build(200, "商品下架成功");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("~~~~~~~商品下架失败" + e.getMessage());
			return SysResult.build(201, "商品下架失败!请联系管理员");
		}

	}

	@RequestMapping("/reshelf")
	@ResponseBody
	public SysResult reshelfItem(Long[] ids) {
		try {
			itemService.reshelfItem(ids);
			return SysResult.build(200, "商品上架成功");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("~~~~~~~商品上架失败" + e.getMessage());
			return SysResult.build(201, "商品上架失败!请联系管理员");
		}
	}
    @RequestMapping("/desc/{itemId}")
    @ResponseBody
    public SysResult findItemDesc(@PathVariable Long itemId){
    	try {
    		ItemDesc itemDesc=itemService.findItemDesc(itemId);
			return SysResult.oK(itemDesc);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("~~~~~~~查询商品描述信息失败" + e.getMessage());
			return SysResult.build(201, "查询商品描述信息失败!请联系管理员");
		}
    }
}
