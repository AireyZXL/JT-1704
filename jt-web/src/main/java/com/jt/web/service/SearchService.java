/**
 * 
 */
package com.jt.web.service;

import java.io.IOException;
import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.jt.web.pojo.Item;

/**
 * @author Airey
 * @date   2017年8月16日
 */
@Service
public class SearchService {
	@Autowired
	private HttpSolrClient httpSolrClient;

	// 从solr获取数据
	public List<Item> query(String key, @RequestParam(defaultValue = "1") Integer page,
			@RequestParam(defaultValue = "20") Integer rows) throws SolrServerException, IOException {
		// 创建一个查询对象
		SolrQuery sq = new SolrQuery();
		sq.setQuery("title:" + key);// 查询表达式，title：三星
		Integer startPos = (Math.max(page, 1) - 1) * rows;
		sq.setStart(startPos);// 开始的位置
		sq.setRows(rows);// 从开始的位置取多少条记录
		// 设置一些参数，查询关键字，当前页，每页的记录数
		// 查询后的结果集
		QueryResponse response = httpSolrClient.query(sq);
		List<Item> itemList = response.getBeans(Item.class);
		return itemList;
	}

}
