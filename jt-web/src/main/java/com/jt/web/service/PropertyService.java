/**
 * 
 */
package com.jt.web.service;

import org.springframework.stereotype.Service;

import com.jt.common.spring.exetend.PropertyConfig;

/**
 * @author Airey
 * @date   2017年8月5日
 */
@Service
public class PropertyService {
	@PropertyConfig
	public String MANAGE_URL;
}
