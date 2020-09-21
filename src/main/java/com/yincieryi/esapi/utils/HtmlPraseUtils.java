package com.yincieryi.esapi.utils;

import com.yincieryi.esapi.pojo.PojoContext;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HtmlPraseUtils {

	public static void main(String[] args) throws IOException {
		
//		//解析
//		String url = "https://search.jd.com/Search?keyword=java";
//
////		Jsoup jsoup = new Jsoup();
//
//		//获取浏览器的对象
//		Document document = Jsoup.parse(new URL(url), 30000);
//		//所有js可以使用对象都能使用
//		Element element = document.getElementById("J_goodsList");
////		System.out.println(element.html());
//		//获取所有li元素
//		Elements li = element.getElementsByTag("li");
//		for (Element el : li) {
//
//			String attr = el.getElementsByTag("img").eq(0).attr("data-lazy-img");
//
//			String price = el.getElementsByClass("p-price").eq(0).text();
//			String title = el.getElementsByClass("p-name").eq(0).text();
//
//			System.out.println("=========================");
//			System.out.println(attr);
//			System.out.println(price);
//			System.out.println(title);
//		}
		new HtmlPraseUtils().parseId("C++").forEach(System.out::println);
	}
	
	public static List<PojoContext> parseId(String keyWord) throws IOException {
		//解析
		String url = "https://search.jd.com/Search?keyword="+keyWord;

//		Jsoup jsoup = new Jsoup();
		
		//获取浏览器的对象
		Document document = Jsoup.parse(new URL(url), 30000);
		//所有js可以使用对象都能使用
		Element element = document.getElementById("J_goodsList");
//		System.out.println(element.html());
		//获取所有li元素
		Elements li = element.getElementsByTag("li");
		
		ArrayList<PojoContext> list = new ArrayList<>();
		for (Element el : li) {
			
			String attr = el.getElementsByTag("img").eq(0).attr("data-lazy-img");
			String price = el.getElementsByClass("p-price").eq(0).text();
			String title = el.getElementsByClass("p-name").eq(0).text();
			
			list.add(new PojoContext(price, title, attr));
			System.out.println("=========================");
			System.out.println(attr);
			System.out.println(price);
			System.out.println(title);
			
		}
		
		return list;
	}

}
