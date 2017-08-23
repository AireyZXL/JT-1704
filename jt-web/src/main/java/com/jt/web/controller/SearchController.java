/**
 * 
 */
package com.jt.web.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jt.web.pojo.Item;
import com.jt.web.service.SearchService;

/**
 * @author Airey
 * @date   2017年8月16日
 */
@Controller
public class SearchController {
	@Autowired
	private SearchService searchService;
	// 转向查询结果页面 /search.html?q=java
	@RequestMapping("/search")
	public String searcher(String q, Integer page, Integer rows, Model model) throws SolrServerException, IOException {
		// 准备数据
		try {
			q = new String(q.getBytes("ISO-8859-1"), "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		model.addAttribute("query", q);
		List<Item> itemList = searchService.query(q, page, rows);
		model.addAttribute("itemList", itemList);
		return "search";
	}
}
