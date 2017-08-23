/**
 * 
 */
package com.jt.web.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.service.HttpClientService;
import com.jt.common.service.RedisService;
import com.jt.common.spring.exetend.PropertyConfig;
import com.jt.web.pojo.Item;
import com.jt.web.pojo.ItemDesc;

/**
 * @author Airey
 * @date   2017年8月3日
 */
@Service
public class ItemService {
	@Autowired
	private HttpClientService httpClientService;
	private static final ObjectMapper MAPPER = new ObjectMapper();
	@PropertyConfig
	private String MANAGE_URL; // 从service层注入属性
	// 定义一个前缀，公用
	@PropertyConfig
	private String ITEM_KEY_PREFIX;
	// 分片方式
	@Autowired
	private RedisService redisService;

	/**
	 *从后台查询商品详情，使用httpClient方式
	 * @param itemId
	 * @return
	 */
	public Item getItemById(Long itemId) {
		// 利用httpClient方式返回后台系统
		String url = MANAGE_URL + "/web/item/" + itemId;
		try {
			// 1.判断缓存是否有数据
			String ITEM_KEY = ITEM_KEY_PREFIX + itemId;
			String jsonData = redisService.get(ITEM_KEY);
			if (StringUtils.isEmpty(jsonData)) {
				// 无数据 
				jsonData=httpClientService.doGet(url);
				redisService.set(ITEM_KEY, jsonData);// 设置redis 
			}

			// 怎么转换成单个对象
			Item item = MAPPER.readValue(jsonData, Item.class);
			return item;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return null;
	}

	/**
	 * //从后台查询商品描述，使用httpClient方式
	 * @param itemId
	 * @return
	 */
	public ItemDesc getItemDescByItemId(Long itemId) {
		String url = MANAGE_URL + "/web/item/desc/" + itemId;
		try {
			String jsonData = httpClientService.doGet(url);
			return MAPPER.readValue(jsonData, ItemDesc.class);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return null;
	}

}
