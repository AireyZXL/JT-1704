/**
 * 
 */
package com.jsoup;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

/**
 * @author Airey
 * @date   2017年8月16日
 */
public class JsoupJD {
	

	// 获取所有的3级分类
	public static List<String> getAllLevel3() throws IOException {
		List<String> list = new ArrayList<String>();
		String url = "https://www.jd.com/allSort.aspx";
		// 爬虫问题，大量测试抓取的选择器是否是正确的
		Elements els = Jsoup.connect(url).get().select("div dl dd a");// 多级之间用空格隔开
		for (Element ele : els) {
			String href = "http:" + ele.attr("href");
			if (href.startsWith("http://list.jd.com/list.html?cat")) {
				System.out.println(href);
				list.add(href);
			}
		}
		System.out.println("有效的总数:" + list.size());
		return list;
	}

	// 获取某个分类的总页数
	public static Integer getPage(String catUrl) {
		try {
			String text = Jsoup.connect(catUrl).get().select("#J_topPage .fp-text i").get(0).text();
			Integer pageNum = Integer.parseInt(text);
			return pageNum;
		} catch (IOException e) {
			e.printStackTrace();
			// 有些如果不正确的就忽略
			return 0;
		}
	}

	// 获取某个分类下的所有列表页面链接
	public static List<String> getPageUrls(String catUrl) {
		List<String> pageUrls = new ArrayList<String>();
		Integer pageNum = JsoupJD.getPage(catUrl);
		for (int i = 0; i <= pageNum; i++) {
			pageUrls.add(catUrl + "&page=" + i);

		}
		return pageUrls;
	}

	// 获取列表页面中的所有商品链接
	public static List<String> getItemUrls(String pageUrl) {
		List<String> itemList = new ArrayList<String>();
		
		try {
			Elements els = Jsoup.connect(pageUrl).get().select(".gl-i-wrap ").select(".j-sku-item .p-img a");
			for (Element ele : els) {
				String href = "http:" + ele.attr("href");
				System.out.println(href);
				itemList.add(href);
			}
			return itemList;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	
	
	@Test
	public void run() throws IOException {
		// JsoupJD.getAllLevel3();
		String url = "http://list.jd.com/list.html?cat=1713,4855,4882&page=65";
		// System.out.println(JsoupJD.getPage(url));
		//JsoupJD.getPageUrls(url);
		JsoupJD.getItemUrls(url);
		
	}
}
