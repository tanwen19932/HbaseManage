package edu.buaa.nlp.tw.common;

import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Document.OutputSettings;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;
import org.jsoup.safety.Whitelist;
import org.jsoup.select.Elements;

public class HtmlUtil {
	private static Map<String, Object> domainMap = new TreeMap<>();

	static {
		domainMap.put("com", 0);
		domainMap.put("tel", 0);
		domainMap.put("mobi", 0);
		domainMap.put("net", 0);
		domainMap.put("org", 0);
		domainMap.put("asia", 0);
		domainMap.put("me", 0);
		domainMap.put("tv", 0);
		domainMap.put("biz", 0);
		domainMap.put("cc", 0);
		domainMap.put("name", 0);
		domainMap.put("info", 0);
		domainMap.put("gov", 0);
		domainMap.put("cn", 0);
		domainMap.put("co", 0);
		domainMap.put("edu", 0);
		domainMap.put("europa", 0);
	}

	public static String getDomain2(String url) {
		Pattern pattern = Pattern
				.compile("(?=.{3,255})[a-zA-Z0-9][-a-zA-Z0-9]{0,62}(\\.[a-zA-Z0-9][-a-zA-Z0-9]{0,62})+");
		Matcher matcher = pattern.matcher(url.replaceAll("\\u00A0*$", "").replaceAll("^\\u00A0*", "").trim());
		String url2 = null;
		if (matcher.find()) {
			url2 = matcher.group(0);
		} else
			return null;
		String domain = url2.replaceAll("^www.*?\\.", "");
		String[] domains = domain.split("\\.");
		for (int i = 1; i < domains.length; i++) {
			String temp = domains[i];
			if (check(temp)) {
				return new StringBuffer().append(domains[i - 1]).append(".").append(temp).toString();
			}
		}
		return domain;
	}

	public static String getDomain(String url) {
		Pattern pattern = Pattern
				.compile("(?=.{3,255})[a-zA-Z0-9][-a-zA-Z0-9]{0,62}(\\.[a-zA-Z0-9][-a-zA-Z0-9]{0,62})+");
		Matcher matcher = pattern.matcher(url.replaceAll("\\u00A0*$", "").replaceAll("^\\u00A0*", "").trim());
		String url2 = null;
		if (matcher.find()) {
			url2 = matcher.group(0);
		} else
			return null;
		String domain = url2.replaceAll("^www.*?\\.", "");
		String[] domains = domain.split("\\.");
		for (int i = 1; i < domains.length; i++) {
			String temp = domains[i];
			if (check(temp)) {
				return new StringBuffer().append(domains[i - 1]).append(".").append(temp).toString();
			}
		}
		return domain;
	}

	private static boolean check(String domain) {
		if (domainMap.get(domain) != null) {
			return true;
		} else
			return false;
	}

	public static String htmlRemoveTag(String inputString) {
		if (inputString == null)
			return null;
		String htmlStr = inputString; // 含html标签的字符串
		String textStr = "";
		Pattern p_script;
		Matcher m_script;
		Pattern p_style;
		Matcher m_style;
		Pattern p_html;
		Matcher m_html;
		try {
			// 定义script的正则表达式{或<script[^>]*?>[\\s\\S]*?<\\/script>
			String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>";
			// 定义style的正则表达式{或<style[^>]*?>[\\s\\S]*?<\\/style>
			String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>";
			String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式
			p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
			m_script = p_script.matcher(htmlStr);
			htmlStr = m_script.replaceAll(""); // 过滤script标签
			p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
			m_style = p_style.matcher(htmlStr);
			htmlStr = m_style.replaceAll(""); // 过滤style标签
			p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
			m_html = p_html.matcher(htmlStr);
			htmlStr = m_html.replaceAll(""); // 过滤html标签
			textStr = htmlStr;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return textStr;// 返回文本字符串
	}

	public static String rmTagsByJsoup(String html , String ...tags) {
        Whitelist whitelist = Whitelist.none();
        whitelist.addTags( tags );
        OutputSettings settings = new OutputSettings();
        settings.prettyPrint(false);
        return Jsoup.clean( html,"", whitelist, settings);
	}
	
	public static String rmTagsByJsoupWithBr(String html) {
		return rmTagsByJsoup(html, "br" ,"p");
	}

	public static void main(String[] args) {
		String url = "http://my.oschina.net/KingSirLee/blog/381726";
		System.out.println(getDomain(url));
		String html = "C&#96C6;&#x56E2;&#x5929;c&#x6D25;&#x5927;&#x5510;&#x56FD;&#x9645;&#x76D8;&#x5C71;&#x53D1;&#x7535;&#x6709;&#x9650;&#x8D23;&#x4EFB;&#x516C;&#x53F8;";
		System.out.println(rmTagsByJsoupWithBr(html));
	}

}
