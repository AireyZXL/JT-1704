/**
 * 
 */
package com.jt.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jt.common.service.RedisService;

/**
 * @author Airey
 * @date   2017年8月15日
 */
@Service //spring包扫描创建这个service，在rabbitmq中就可以注入
public class RabbitItemService {
	@Autowired
	private RedisService redisService;
	
    public void updateItem(String itemId) {
		redisService.del(itemId);
	}
}
