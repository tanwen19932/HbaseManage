package chao;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 处理HTML网页标签的工具类
 * @author Vincent
 *
 */
public class HtmlUtil {

	private static final String regHtml="<[^>]+>|&[a-zA-Z\\s]+;";
	private static final String regBlank="\t|\r|\n";
	private static final String regEnter="\\r\\n|\\n";
	private static final String regBR="##br##";
	
	private static Pattern patCompile = Pattern.compile("&#.*?;");
	private static String regHTMLNumcode = "&#(\\d{2,5});";   
	private static Pattern patHTMLNumCode = Pattern.compile(regHTMLNumcode);
	
	private static Map<String,String> mapHtmlCode = null;
	
	static{
		mapHtmlCode = new HashMap<String,String>();
		try
		{
			BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(new FileInputStream("data/htmlcode.txt"),"UTF-8"));
			String temp = null;
			while( (temp=bufferedReader.readLine())!=null)
			{
				if(temp.trim().isEmpty()) continue;
				String[] splitStrings = temp.trim().split("\t");
				// 有些hash文件有异常
				if(splitStrings.length >= 3)
				{
					try {
						String htmlCode = splitStrings[1].trim();
						String realChar = splitStrings[0].trim();
						if(realChar.isEmpty()) realChar = " ";
						String numCode = splitStrings[2];
						mapHtmlCode.put(htmlCode,realChar);
					}catch (Exception e){
						continue;
					}
				}
			}
			bufferedReader.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
	public static String delHtmlTag(String text){
		return text.replaceAll(regHtml, "");
	}
	
	public static String delBlank(String text){
		return text.replaceAll(regBlank, "");
	}
	
	public static String delEnter(String text){
		return text.replaceAll(regEnter, "");
	}
	
	public static String RemoveStyleCode(String content) {

		try {
			Pattern p1 = Pattern.compile("(?s)<script\\s*.*?>(.*?)</script>",
					Pattern.CASE_INSENSITIVE);
			Matcher m1 = p1.matcher(content);
			content = m1.replaceAll("");

			Pattern p2 = Pattern.compile("(?s)<style\\s*.*?>(.*?)</style>",
					Pattern.CASE_INSENSITIVE);
			Matcher m2 = p2.matcher(content);
			content = m2.replaceAll("");

			Pattern p11 = Pattern.compile("(?s)<script\\s*.*?/>",
					Pattern.CASE_INSENSITIVE);
			Matcher m11 = p11.matcher(content);
			content = m11.replaceAll("");

			Pattern p21 = Pattern.compile("(?s)<style\\s*.*?/>",
					Pattern.CASE_INSENSITIVE);
			Matcher m21 = p21.matcher(content);
			content = m21.replaceAll("");
			// 去除注释
			Pattern p3 = Pattern.compile("<!--.*?-->");
			Matcher m3 = p3.matcher(content);
			content = m3.replaceAll("");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return content;

	}

	public static String clearTitleHTML(String content)
	{
		try
		{
			content = RemoveStyleCode(content);
			content = content.replaceAll("<[^>]*>", " ");						
			content = content.replaceAll("\\s+", " ");
			return content;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return "";
		}
	}

	public static String clearHTML(String content)
	{
		try
		{
			content = RemoveStyleCode(content);
			content = content.replaceAll("(\\n\\s*)+", "##br##");
			content = content.replaceAll("<(p|P)[^>]*>", "##br##");
			content = content.replaceAll("<(div|DIV)[^>]*>", "##br##");
			content = content.replaceAll("<(br|BR)\\s*/?>", "##br##");
			content = content.replaceAll("<[^>]*>", " ");						
			content = content.replaceAll("##br##", "<BR/>");
			content = content.replaceAll("##page:\\s*\\d+\\s*##", " ");
			content = content.replaceAll("#page:\\s*\\d+\\s*#", " ");
			content = content.replaceAll("\\s+第[一|二|三|1|2|3]页\\s+", " ");
			content = content.replaceAll("\\s+[上|下]一页\\s+", " ");
			content = content.replaceAll("1[ \t]+2(.+)?下一页", " ");
			
			content = content.replaceAll("(<BR/>\\s*)+", "<BR/>");
			content = content.replaceAll("\\s+", " ");
			content = HTMLDecode(content);
			return content;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return "";
		}
	}
	

	/**
	 * 将&#\d{5};类型的编码转换成实际的字符
	 * @param str
	 * @return
	 */
    public static String HTMLDecode(String str){ 
    	try
    	{
			// 去掉一些HTML编码
    		
    		for(String key : mapHtmlCode.keySet())
    		{
    			str = str.replaceAll(key, mapHtmlCode.get(key));
    		}
    		str = str.replaceAll("&nbsp;", " ");
    		
/*			str = str.replaceAll("&quot;", "\"");
			str = str.replaceAll("&nbsp;", " ");
			str = str.replaceAll("&middot;", "·");
			str = str.replaceAll("&amp;", "&");
			str = str.replaceAll("&ldquo;", "“");
			str = str.replaceAll("&rdquo;", "”");
			str = str.replaceAll("&gt;", ">");
			str = str.replaceAll("&lt;", "<");
			str = str.replaceAll("&raquo;", "?");
			str = str.replaceAll("&times;", "×");
			str = str.replaceAll("&ccedil;", "?");
			str = str.replaceAll("&atilde;", "?");
			str = str.replaceAll("&ecirc;", "ê");
			str = str.replaceAll("&apos;","'");*/
			
	     	//str = str.replaceAll("&#39;", "'");	
	     	str = str.replaceAll("&#x09;", "	");	
		    Matcher matcher = patHTMLNumCode.matcher(str);     
	        while(matcher.find())
	        {
	        	try
	        	{
//	        		str = matcher.replaceFirst(String.valueOf((char) Integer.parseInt(matcher.group(1))));
//	        		matcher = patHTMLNumCode.matcher(str);
	        		str = str.replace(matcher.group(),String.valueOf((char) Integer.parseInt(matcher.group(1))));
	        		matcher = patHTMLNumCode.matcher(str);
	        	}
	        	catch(Exception e)
	        	{
	        		e.printStackTrace();
	        		break;
	        	}
	        }
	        
	        matcher = patCompile.matcher(str);  
	        // 循环搜索 并转换 替换  
	        while (matcher.find()) {  
	        	try
	        	{
		            String group = matcher.group();  
		            // 获得16进制的码  
		            String hexcode = "0" + group.replaceAll("(&#|;)", "");  
		            // 字符串形式的16进制码转成int并转成char 并替换到源串中  
		            str = str.replaceAll(group, (char) Integer.decode(hexcode).intValue() + "");  
	        	}
	        	catch(Exception e)
	        	{
	        		e.printStackTrace();
	        		break;
	        	}
	        }  
	        
	        
	    	return str;   
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    		return str;
    	}
    	
        //去掉一些HTML编码
    	/*
        String[] tmp = str.split(";&#|&#|;");  
        StringBuffer sb = new StringBuffer("");  
          
        for (int i=0; i<tmp.length; i++ ){  
            if (tmp[i].matches("\\d{5}")){  
                sb.append((char)Integer.parseInt(tmp[i]));  
            } else {  
                sb.append(tmp[i]);  
            }  
        }  
        str = sb.toString();

        return str;
        */
    }  
    		
	
	public static void main(String[] args) {
		System.out.println(delHtmlTag("< br/> NEW YORK( Reuters)-U.S. st"));
		String txt = "C&#96C6;&#x56E2;&#x5929;c&#x6D25;&#x5927;&#x5510;&#x56FD;&#x9645;&#x76D8;&#x5C71;&#x53D1;&#x7535;&#x6709;&#x9650;&#x8D23;&#x4EFB;&#x516C;&#x53F8;";
		txt = "&apos;60 Minutes&apos; &#39;report: Russian gold medalists took steroids";
		txt = "&lsquo;Mastermind&rsquo; of Saudi consulate attack killed, three held - Newspaper - DAWN.COM";
		txt = "CHICAGO (AP) - A Chicago singer who appeared on the Mexican version of \"The Voice\" in 2011 has died after he was shot in an ambush while celebrating his birthday with friends.  WGN television said police were investigating whether the shooting was an attempted carjacking or a robbery";
		
		txt = "Posted: 8:47 AM || Last Modified: 8:48 AM##br####br## Southampton's Jack Cork, left, and Everton's Steven Naismith in action during their English Premier League soccer match at St Mary's ground in Southampton, England, Saturday April 26, 2014. (AP Photo / Chris Ison, PA) UNITED KINGDOM OUT - NO SALES - NO ARCHIVES##br####br## SOUTHAMPTON, England - Everton self-destructed in its attempt to earn a Champions League spot, scoring two own goals in a 2-0 loss to Southampton on Saturday that seriously dents the team's chances of finishing in the top four in the Premier League.##br####br## Antolin Alcaraz headed into his own net in the opening minute, and Seamus Coleman repeated the feat in the 31st as Everton missed a chance to provisionally move above Arsenal into fourth place. Arsenal remained one point ahead with a game in hand, with Everton having just two matches remaining.##br####br## Romelu Lukaku missed two good scoring chances for Everton in the first half and Leon Osman appealed for a penalty in the 62nd minute but was booked for diving instead.##br####br## Southampton is lodged firmly in eighth place on 52 points.##br####br##";
		
		System.out.println(clearHTML(txt));
		
		
/*		txt = "<br>aa<br/>aa<P>ddd<p/>dfasdf<BR /> asdfasdf";
		System.out.println(txt);
		System.out.println(clearHTML(txt));*/
		
		
		
	}
}
