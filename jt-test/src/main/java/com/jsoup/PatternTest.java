/**
 * 
 */
package com.jsoup;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Airey
 * @date   2017年8月19日
 */
public class PatternTest {

	public static void main(String[] args) {
		//testLevel2();
		 testLevel3();
	}

	public static void testLevel2() {
		String url = "//category.vip.com/search-1-0-1.html?q=2|29741|&rp=30074|0&ff=women|0|1|0";
		Pattern pattern = Pattern.compile("^//category.vip.com/.+(\\?q=2\\|)(\\d+)(\\|&rp=)(\\d+)(.+)$");

		Matcher matcher = pattern.matcher(url);
		if (matcher.find()) {
			// System.out.println(matcher.group(1));
			System.out.println(matcher.group(2));// 29741
			// System.out.println(matcher.group(3));
			System.out.println(matcher.group(4));// 30074
			// System.out.println(matcher.group(5));
		}
	}

	public static void testLevel3() {
		//http://category.vip.com/search-1-0-1.html?q=3|30043||&rp=30074|29741&ff=women|0|1|2
		//String url = "//category.vip.com/search-1-0-1.html?q=3|30057||&rp=30074|29741&ff=women|0|1|1";
		String url="http://category.vip.com/search-1-0-1.html?q=3|30043||&rp=30074|29741&ff=women|0|1|2";
		//Pattern pattern = Pattern.compile("^//category.vip.com/.+(\\?q=3\\|)(\\d+)(\\|\\|&rp=)(\\d+)\\|(\\d+)(.+)$");
		Pattern pattern = Pattern.compile("^http://category.vip.com/.+(\\?q=3\\|)(\\d+)(\\|\\|&rp=)(\\d+)\\|(\\d+)(.+)$");

		Matcher matcher = pattern.matcher(url);
		if (matcher.find()) {
			// System.out.println(matcher.group(0));
			// System.out.println(matcher.group(1));
			System.out.println(matcher.group(2));// 30057
			// System.out.println(matcher.group(3));
			// System.out.println(matcher.group(4));// 30074
			System.out.println(matcher.group(5));// 29741
			// System.out.println(matcher.group(6));
		}
	}

}
