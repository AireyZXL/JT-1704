/**
 * 
 */
package com.jt.manage.service;

import java.io.IOException;
import java.security.interfaces.DSAKey;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.service.BaseService;
import com.jt.common.service.RedisService;
import com.jt.common.vo.ItemCatData;
import com.jt.common.vo.ItemCatResult;
import com.jt.manage.mapper.ItemCatMapper;
import com.jt.manage.pojo.ItemCat;

import redis.clients.jedis.JedisCluster;

/**
 * @author Airey
 * @date   2017年7月24日
 */
@Service
public class ItemCatService extends BaseService<ItemCat> {
	@Autowired
	private ItemCatMapper itemCatMapper;
	@Autowired
	// 注入伪service
	 private RedisService redisService;
	//private JedisCluster jedisCluster;// 集群
	// 引入java对象和json串转换对象ObjectMapper；全局唯一
	private static final ObjectMapper MAPPER = new ObjectMapper();
	private static final Logger log = Logger.getLogger(ItemCatService.class);

	// 到后台查询商品分类，返回java对象，列表
	public List<ItemCat> findItemCatList(Long parentId) {

		/**
		 * 如果传入的是对象,那么查询时就会根据对象的属性值添加where 条件
		 * select * from tb_item_cat where id = ?
		 */
		ItemCat itemCat = new ItemCat();
		itemCat.setParentId(parentId);

		// 1.判断缓存中是否有数据
		String ITEM_CAT_KEY = "ITEM_CAT_" + parentId; // 保证整个项目中唯一
		String jsonData = redisService.get(ITEM_CAT_KEY);
		if (StringUtils.isNotEmpty(jsonData)) { // 代表缓存中有数据
			// 如果自己try-catch，spring对service事务作废
			JsonNode jsonNode;
			try {
				jsonNode = MAPPER.readTree(jsonData);
				// 把Json串转成成java对象，List<ItemCat>
				Object obj = null;
				if (jsonNode.isArray() && jsonNode.size() > 0) {
					obj = MAPPER.readValue(jsonNode.traverse(),
							MAPPER.getTypeFactory().constructCollectionType(List.class, ItemCat.class));
				}
				return (List<ItemCat>) obj;
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		} else {
			// 2.执行业务
			List<ItemCat> itemCatList = itemCatMapper.select(itemCat);

			// 3.保存数据到缓存中，把java对象转成json字符串
			try {
				String json = MAPPER.writeValueAsString(itemCatList);
				redisService.set(ITEM_CAT_KEY, json);
			} catch (Exception e) {
				log.error(e.getMessage());
				e.printStackTrace();
			}
			return itemCatList;
		}
		return null;
	}

	// 为前台来准备数据
	public ItemCatResult getItemCat() {
		/*
		 * 步骤： 1、获取所有的数据 2、一次循环获取当前节点的所有的子节点 3、三级遍历组织数据
		 */
		List<ItemCat> cats = itemCatMapper.select(null);
		// 构建一个map，里面存放当前节点下的，所有的子节点数据
		Map<Long, List<ItemCat>> map = new HashMap<Long, List<ItemCat>>();
		for (ItemCat cat : cats) {
			// 先判断这个key是否存在
			if (!map.containsKey(cat.getParentId())) {
				// 不存在，创建key，并创建List
				map.put(cat.getParentId(), new ArrayList<ItemCat>());
			}
			map.get(cat.getParentId()).add(cat);
		}

		// 一级菜单
		ItemCatResult result = new ItemCatResult();
		List<ItemCatData> list1 = new ArrayList<ItemCatData>();
		// 遍历一级菜单
		String url = "";
		String name = "";
		for (ItemCat cat1 : map.get(0L)) {
			ItemCatData d1 = new ItemCatData();
			url = "/products/" + cat1.getId() + ".html";
			d1.setUrl(url);
			name = "<a href=\"" + url + "\">" + cat1.getName() + "</a>";
			d1.setName(name);
			// 遍历二级菜单
			List<ItemCatData> list2 = new ArrayList<ItemCatData>();
			for (ItemCat cat2 : map.get(cat1.getId())) {
				ItemCatData d2 = new ItemCatData();
				url = "/products/" + cat2.getId() + ".html";
				d2.setUrl(url);
				d2.setName(cat2.getName());
				// 遍历三级菜单
				List<String> list3 = new ArrayList<String>();
				for (ItemCat cat3 : map.get(cat2.getId())) {
					url = "/products/" + cat3.getId() + ".html";
					list3.add(url + "|" + cat3.getName());
				}
				d2.setItems(list3);
				list2.add(d2);
			}
			d1.setItems(list2);// 二级菜单
			list1.add(d1);
			result.setItemCats(list1);
		}
		return result;
	}
}
