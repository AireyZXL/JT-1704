/**
 * 
 */
package com.jsoup;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * @author Airey
 * @date   2017年8月17日
 */
public class JsoupVip {
	@Test
	public void run() throws IOException {
		File file = new File("D:\\vip.html");
		// JsoupVip.getAllLevel3(file);

		// JsoupVip.getPageNum(catUrl);

		// JsoupVip.getPageUrls(catUrl);

		String catUrl = "https://category.vip.com/search-1-0-1.html?q=3|29734||&rp=30071|29737&ff=beauty|0|1|1";
		// JsoupVip.getItemUrls(pageUrl);

		/*
		 * List<String> pageUrls = JsoupVip.getPageUrls(catUrl); for (String url
		 * : pageUrls) { JsoupVip.getItemUrls(url); }
		 */

		String url = "https://category.vip.com/search-1-0-1.html?q=3|29574||&rp=30066|29992&ff=sports|0|1|1";
		List<String> itemUrlList = JsoupVip.getItemUrls(url);

		for (String itemUrl : itemUrlList) {
			JsoupVip.getTitle(itemUrl);
			JsoupVip.getPrice(itemUrl);
			JsoupVip.getImage(itemUrl);

		}

	}

	@Test
	public void priceBug() {
		String url = "http://www.vip.com/detail-1489699-264944543.html";
		System.out.println(JsoupVip.getPrice(url));
	}
	// 获取商品详细信息

	// 获取商品的详细信息 http://item.jd.com/11384983200.html
	/*
	 * public static Item getItem(String itemUrl) { Item item = new Item();
	 * String s = itemUrl.substring(itemUrl.lastIndexOf("/") + 1,
	 * itemUrl.lastIndexOf(".")); item.setId(Long.parseLong(s));
	 * 
	 * item.setTitle(JsoupJD.getTitle(itemUrl));
	 * item.setSellPoint(JsoupJD.getSellPoint(item.getId()));
	 * 
	 * item.setPrice(JsoupJD.getPrice(item.getId()));
	 * item.setImage(JsoupJD.getImage(itemUrl));
	 * 
	 * // 描述 String itemDesc = JsoupJD.getItemDesc(item.getId());
	 * 
	 * // System.out.println(item); // System.out.println(itemDesc);
	 * 
	 * return item; }
	 */

	// 获取所有的三级分类
	public static List<String> getAllLevel3(File file) throws IOException {
		List<String> levelList = new ArrayList<String>();
		Document doc = Jsoup.parse(file, "utf-8");
		Elements els = doc.select(".cate-list-mores .cate-fix a");
		for (Element ele : els) {
			// System.out.println(ele);
			String href = "http:" + ele.attr("href");
			if (href.startsWith("http://category.vip.com")) {
				System.out.println(href);
				levelList.add(href);
			}
		}
		System.out.println("商品总类的个数:" + levelList.size());
		return levelList;
	}

	// 获取某个分类的总页数
	private static Integer getPageNum(String catUrl) {
		try {
			String text = Jsoup.connect(catUrl).get().select(".cat-oper-wrap div div span").get(0).text();
			// System.out.println(text); 1/10
			String[] num = text.split("/");
			// System.out.println(Arrays.toString(num));
			Integer pageNum = Integer.parseInt(num[1]);
			return pageNum;
		} catch (IOException e) {
			return 0; // 有些如果不正确的，就忽略
		}
	}

	// 获取某个分类下的所有的列表页面链接
	// http://category.vip.com/search-1-0-16.html?q=3|30037||&rp=30074|30063#J_catSite
	public static List<String> getPageUrls(String catUrl) {
		List<String> pageUrlsList = new ArrayList<String>();
		Integer pageNum = JsoupVip.getPageNum(catUrl);
		System.out.println("一共有: " + pageNum + "页");
		Pattern pattern = Pattern.compile("^(.+)(1.html)(.+)$");
		Matcher matcher = pattern.matcher(catUrl);
		if (matcher.find()) {
			for (int i = 1; i <= pageNum; i++) {
				String url = matcher.group(1) + i + ".html" + matcher.group(3);
				pageUrlsList.add(url);
			}
		}
		return pageUrlsList;
	}

	// 获取列表页面中的所有的商品链接
	public static List<String> getItemUrls(String pageUrl) {
		List<String> itemList = new ArrayList<String>();
		try {
			Elements els = Jsoup.connect(pageUrl).get().select("div .goods-slide .goods-image a");
			for (Element ele : els) {
				String href = "http:" + ele.attr("href");
				// System.out.println(href);
				itemList.add(href);
			}
			System.out.println(itemList.size());
			return itemList;
		} catch (IOException e) {
			return null;
		}
	}

	// 获取某个商品的标题
	public static String getTitle(String itemUrl) {
		String title = "";
		try {
			title = Jsoup.connect(itemUrl).get().select(".pi-title-box .pib-title p").get(1).text();
			System.out.println(title);
		} catch (Exception e) {
			return null;
		}

		return title;
	}

	// 获取某个商品的价格，ajax,json
	public static Long getPrice(String itemUrl) {
		String price = "";
		try {
			price = Jsoup.connect(itemUrl).get().select("#J-pi-price-box span em").text();
			System.out.println(price);

		} catch (Exception e) {
			return null;
		}
		Long p = Long.parseLong(price);

		return p * 100;
	}

	// 获取某个商品的图片
	// TODO
	public static String getImage(String itemUrl) {
		String imgUrl = "";
		try {
			// Elements elements =
			// Jsoup.connect(itemUrl).get().select(".pic-sliderwrap div a");
			Elements elements = Jsoup.connect(itemUrl).get().select("div .show-midpic a");
			for (Element ele : elements) {
				String href = "http:" + ele.attr("href");
				imgUrl += href + ",";
			}
		} catch (Exception e) {
			return null;
		}
		imgUrl = imgUrl.substring(0, imgUrl.length() - 1);
		System.out.println(imgUrl);
		return imgUrl;
	}

}
