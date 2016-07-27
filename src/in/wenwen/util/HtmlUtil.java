/*
 * Copyright (c) jbing.org
 */
package in.wenwen.util;

import java.util.ArrayList;
import java.util.List;

import org.jiucheng.util.StringUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.safety.Whitelist;
import org.jsoup.select.Elements;

/**
 * @version 0.0.1
 */
public class HtmlUtil {
	
	// 只有纯文本可以通过
	public static String getText(String html) {
		if (html == null)
			return "";
		return Jsoup.clean(html, Whitelist.none());
	}
	
	// 以下标签可以通过
	// b, em, i, strong, u. 纯文本 
	public static String getSimpleHtml(String html) {
		if (html == null)
			return null;
		return Jsoup.clean(html, Whitelist.simpleText());
	}
	
	// 以下标签可以通过
	//a, b, blockquote, br, cite, code, dd, dl, dt, em, i, li, ol, p, pre, q, small, strike, strong, sub, sup, u, ul
	public static String getBasicHtml(String html) {
		if (html == null)
			return null;
		return Jsoup.clean(html, Whitelist.basic());
	}
	
	//在basic基础上  增加图片通过
	public static String getBasicHtmlandimage(String html) {
		if (html == null)
			return null;
		return Jsoup.clean(html, Whitelist.basicWithImages());
	}
	// 以下标签可以通过	
	//a, b, blockquote, br, caption, cite, code, col, colgroup, dd, dl, dt, em, h1, h2, h3, h4, h5, h6, i, img, li, ol, p, pre, q, small, strike, strong, sub, sup, table, tbody, td, tfoot, th, thead, tr, u, ul
	public static String getFullHtml(String html) {
		if (html == null)
			return null;
		return Jsoup.clean(html, Whitelist.relaxed());
	}
	
	//只允许指定的html标签
	public static String clearTags(String html, String ...tags) {
		Whitelist wl = new Whitelist();
		return Jsoup.clean(html, wl.addTags(tags));
	}
	
	// 对关键字加上颜色
	public static String markKeywods (String keywords, String target) {
		if (StringUtil.isNotBlank(keywords)) {
			String[] arr = keywords.split(" ");
			for (String s : arr) {
				if (StringUtil.isNotBlank(s)) {
//					String temp = "<span class=\"highlight\">" + s + "</span>";
//				    String temp = "<span style=\"background-color:#38A3DB;color:#FFFFFF;\">" + s + "</span>";
				    String temp = "<span class=\"mhl\">" + s + "</span>";
					if(temp!=null)
						target = target.replaceAll(s, temp);
				}
			}
		}
		return target;
	}

	// 获取文章中的img url
	public static String getImgSrc(String html) {
		if (html == null)
			return null;
	    Document doc = Jsoup.parseBodyFragment(html);
	    Element image = doc.select("img").first();
	    return image == null ? null : image.attr("src");
	}
	/** 
	 * 解释HTML获取图片列表 
	 * @param html HTML内容 
	 * @return 图片列表 
	 */  
	public static List<String> getImgSrcs(String html){  
	    List<String> imgSrcs= new ArrayList<String>();  
	        
	    Document doc = Jsoup.parse(html);  
	    Elements imgs = doc.getElementsByTag("img");  
	    for (Element img : imgs) {  
	        String imgSrc = img.attr("src");  
	        if(imgSrc!=null && imgSrc.trim().length()>0){  
	            imgSrcs.add(imgSrc);  
	        }  
	    }  
	        
	    return imgSrcs;  
	}
}
