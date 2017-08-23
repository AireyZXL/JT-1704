/**
 * 
 */
package com.jsoup;

import java.io.IOException;

import org.jsoup.Connection;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Airey
 * @date   2017年8月16日
 */
public class TestJsoup {
	private static final ObjectMapper MAPPER = new ObjectMapper();

	@Test
	public void html() throws IOException {
		String url = "http://www.doupocangqiong1.com/";
		Connection connection = Jsoup.connect(url);
		Response response = connection.execute();// 执行链接，返回响应对象
		String html = response.body();
		System.out.println(html);

	}

	@Test
	// 抓取整个网站
	public void site() throws IOException {
		// 把每个页面
		String url = "http://www.doupocangqiong1.com/";
		Document doc = Jsoup.connect(url).get();
		Elements els = doc.getElementsByTag("a");
		for (Element ele : els) {
			System.out.println(ele.attr("href"));
		}

	}

	@Test
	public void json() throws IOException {
		String url = "http://order.jt.com/order/query/31425698029506";
		String json = Jsoup.connect(url).ignoreContentType(true).execute().body();
		System.out.println(json);

		JsonNode jsonNode = MAPPER.readTree(json);
		String orderId = jsonNode.get("orderId").asText();
		System.out.println(orderId);
	}

}
