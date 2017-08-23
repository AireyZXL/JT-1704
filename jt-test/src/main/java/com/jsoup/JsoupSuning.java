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

import javax.swing.plaf.basic.BasicScrollPaneUI.ViewportChangeHandler;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.Connection.Response;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

/**
 * @author Airey
 * @date   2017年8月16日
 */
public class JsoupSuning {

	@Test
	public void testPattern() {
		String usl = "http://www.iteye.com/problems/new&dfsdf=aa";
		// String usl =
		// "http://category.vip.com/search-1-0-1.html?q=3|30037||&rp=30074|30063#J_catSite";

		Pattern pattern = Pattern.compile("^http://(.+?)/(.+?)/.+?&(.+?)$");

		// Pattern pattern2 = Pattern.compile("^(.+?)(1.html)(.+?)$");

		Matcher matcher = pattern.matcher(usl);
		
		if (matcher.find()) {
			System.out.println(matcher.group(0));
			System.out.println(matcher.group(1));
			System.out.println(matcher.group(2));
			System.out.println(matcher.group(3));
		}

	}

	@Test
	public void html() throws IOException {
		String url = "http://category.vip.com/";
		Connection connection = Jsoup.connect(url);
		Response response = connection.execute();// 执行链接，返回响应对象
		String html = response.body();
		System.out.println(html);
	}

	@Test // 抓取整站，把所有的页面都下载下来（静态页面）
	public void site() throws IOException {
		// 找每个页面的所有的连接，两种策略：深度遍历，广度遍历，层数3,5
		String url = "http://category.vip.com/";
		Document doc = Jsoup.connect(url).get(); // 链式编程
		Elements els = doc.getElementsByTag("a");
		for (Element ele : els) {
			System.out.println(ele.attr("href"));
		}
	}

	@Test
	public void smail() throws IOException {

		File file = new File("D:\\vip.html");
		System.out.println(file);
		Document document = Jsoup.parse(file, "utf-8");
		Elements elements = document.select("a");
		for (Element element : elements) {
			System.out.println(element);
		}
		// System.out.println(document);
	}

	@Test
	public void testLevel3() throws IOException {
		JsoupSuning.getAllLevel3();
	}

	// 获取所有的3级分类
	public static List<String> getAllLevel3() throws IOException {
		List<String> levelList = new ArrayList<String>();
		String url = "https://www.s.cn/pa.html";

		// Document doc = Jsoup.connect(url).get();
		// System.out.println(doc);
		Elements elements = Jsoup.connect(url).get().select("a");

		for (Element ele : elements) {
			// System.out.println(ele);
			String href = ele.attr("href");
			System.out.println(href);
			levelList.add(href);
		}
		System.out.println("总数：" + levelList.size());

		return levelList;
	}

}
