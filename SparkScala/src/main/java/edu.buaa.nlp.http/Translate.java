package edu.buaa.nlp.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

import edu.buaa.nlp.util.DupConstants;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

/**
 * 机器翻译工具类
 * @author yuyingen
 *
 */
public class Translate {


	/**
     * 执行一个HTTP POST请求，返回请求响应的
     * @param url     请求的URL地址
     * @param params    请求的查询参数
     * @return 返回请求响应的结果
     */
	public static String translateByPost(String urlStr, String param) {
		if((urlStr == null) || (param == null)
			||	(urlStr.trim().isEmpty()) || (param.trim().isEmpty())
			)
			return "";
		
		HttpURLConnection conn = null;
		String responseContent = "";
		try {
			URL url = new URL(urlStr);
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestProperty("Content-Type", "application/json");
			// url_con.setRequestProperty("Content-type","application/x-java-serialized-object");
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			byte[] b = param.toString().getBytes("utf-8");
			conn.getOutputStream().write(b, 0, b.length);
			conn.getOutputStream().flush();
			conn.getOutputStream().close();

			InputStream in = conn.getInputStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(in, "utf-8"));
			String tempLine = rd.readLine();
			StringBuffer tempStr = new StringBuffer();
			String crlf = System.getProperty("line.separator");
			while (tempLine != null) {
				tempStr.append(tempLine);
				tempStr.append(crlf);
				tempLine = rd.readLine();
			}
			responseContent = tempStr.toString();
			rd.close();
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("repeater error:" + e.getMessage() + e.getCause());
		} finally {
			if (conn != null) {
				conn.disconnect();
			}
		}
		return responseContent;
	}
	
	
	public static String translate(String text) {
		String translateUrl = DupConstants.TRANSLATE_SERVER_URL;
		return translate(translateUrl, text, "en", "zh");
	}
	
	
	
	public static String translate(String urlStr, String text) {
		return translate(urlStr, text, "en", "zh");
	}
	
	
	public static String translate(String urlStr, String text, String srcl, String tgtl) {
		//String param = "{\"srcl\":\""+ srcl +"\",\"tgtl\":\""+ tgtl +"\",\"text\":\""+ text +"\"}";
		//String param = "{\"srcl\":\""+ srcl +"\",\"tgtl\":\""+ tgtl +"\",\"text\":\""+ text +"\"}";
		try {
			JSONObject json = new JSONObject();
			json.put("srcl", srcl);
			json.put("tgtl", tgtl);
			json.put("text", text);
			String postParam = json.toString();
			return translateByPost(urlStr, postParam);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
	}
	
	
	public static String getTranslateFormatResult(String text) {
		String jsonStr = translate(text);
		if((jsonStr==null) || (jsonStr.trim().isEmpty()))
			return "";
		try {
			JSONObject json = JSONObject.fromObject(jsonStr);
			if(json==null || json.isNullObject()) return null;
			JSONArray arr=json.getJSONArray("translation");
			if(arr==null || arr.isEmpty()) return null;
			JSONArray arr2=arr.getJSONObject(0).getJSONArray("translated");
			if(arr2==null || arr2.isEmpty()) return null;
			StringBuffer sb=new StringBuffer();
			for(int i=0; i<arr2.size(); i++){
				JSONObject tran=arr2.getJSONObject(i);
				sb.append(tran.getString("text"));
			}
			return sb.toString();
		} catch (Exception ex) {
			return "";
		}
	}
	
	
	public static void main(String args[]) {
		String text = "Connecting decision makers to a dynamic network of information"; //, people and ideas, Bloomberg quickly and accurately delivers business and financial information, news and insight around the world.Americas+1 212 318 2000Europe, Middle East, & Africa+44 20 7330 7500Asia Pacific+65 6212 1000Connecting decision makers to a dynamic network of information, people and ideas, Bloomberg quickly and accurately delivers business and financial information, news and insight around the world.Americas+1 212 318 2000Europe, Middle East, & Africa+44 20 7330 7500Asia Pacific+65 6212 1000A City of Flint Sewer Department marker flag waves in the wind on a block where lead water lines have started to be replaced on March 17, 2016, in Flint, Michigan.Photographer: Brett Carlsen/Getty Images President Barack Obama’s visit Wednesday to poisoned Flint, Michigan, provides both a balm for residents still fearful of their water supply and an illustration of enduring U.S. economic inequality that has plagued his presidency.The president will receive a briefing on the city’s lead crisis at a local food bank, White House Press Secretary Josh Earnest told reporters Tuesday. He’ll then speak to about 1,000 people at Northwestern High School, where about 90 percent of students qualify for free or reduced-price lunch.Michigan Governor Rick Snyder declared a state of emergency in Flint four months ago over widespread lead contamination in the city’s water supply. Many residents have blamed decisions by Snyder’s administration for the crisis; Michigan Attorney General Bill Schuette on April 20 charged two state officials and one city official with felonies related to the contamination. Snyder, a Republican, has apologized and is drinking water from the city as a show of support for residents.“It’s the most vivid, painful, tragic example that one can imagine of the two Americas,” said Steven McMahon, a Democratic strategist.Long before its lead crisis, Flint was an infamous example of the corners of America hurt worst by globalization and the erosion of U.S. manufacturing. Filmmaker Michael Moore, a Flint native, documented the city’s economic decline in the 1989 film \"Roger and Me.\" About 42 percent of Flint residents lived in poverty in 2014, compared with 16 percent in Michigan overall, according to the U.S. Census, and the median home value was about $37,000, compared with $120,000 for the state.Democratic CampaignObama is not expected to announce new federal aid for the city, Earnest said. A federal emergency declaration earlier this year provided $5 million.“Obviously, there’s been a significant commitment of resources to try to help the people of Flint in this urgent situation,” Earnest said on Monday. Obama has no plans to drink the city’s water, he said. The Environmental Protection Agency says the city’s water is only safe to drink when filtered.Obama’s visit will draw fresh attention to a crisis that has become an emblem of America’s decaying infrastructure and official indifference to the plight of poor and minority communities. The prolonged exposure of Flint’s largely African-American population to unsafe levels of lead in their water already has featured prominently in a Democratic presidential primary campaign centered on economic inequality.Democratic presidential candidate Hillary Clinton came to Flint in February to declare the delay in addressing the city’s contaminated water “immoral.\" The party sited a March debate between Clinton and her opponent, Vermont Senator Bernie Sanders, in the city to highlight the ramifications of the crisis.Obama visited the North American International Auto Show in Detroit in January without stopping at Flint, about an hour’s drive north, drawing criticism from Moore and others.The president’s visit will bring Flint’s “catastrophic situation” back into the national spotlight, where it needs to be, Senator Gary Peters, a Michigan Democrat, said in an interview.“My concern has always been when the media spotlight starts to fade, that focus won’t be there and the problems still will be there,” Peters said. “When the president shows up, that keeps the spotlight on the situation.”The initial indifference to the quality of Flint’s water shown by state officials has special resonance among African-Americans, whose enthusiasm for voting in the fall will be crucial for Democrats without Obama leading the party’s ticket. The city was 57 percent black in 2010, according to the Census. The high school where Obama will speak is 94 percent minority, according to U.S. News and World Report.“In African American communities around the country there’s hyper-awareness of this, a sense that there but for the grace of God could go my community,” McMahon said.Michigan is a reliably Democratic state where Obama won by 10 points over his Republican opponent in 2012, Mitt Romney, whose father was once governor of the state.Michigan’s senators Peters and Debbie Stabenow, also a Democrat, are pressing Congress for $100 million to fix Flint’s water system and for access to $700 million in low-interest federal loans for the city.Snyder’s Crisis \"The big question is when is the state going to jump in as aggressively and effectively as the federal government has?” Stabenow said in an interview.The lead crisis has a partisan cast because emergency city managers appointed bySnyder moved to change the city’s water supply to save money in 2013 and 2014, according to a report commissioned by Snyder’s office. The state Department of Environmental Quality resisted acting on early complaints about water quality from city residents, the report found.Snyder, who said last week his schedule was too busy to accommodate the president, will meet Obama at the airport and attend the briefing, according to his spokesman, Ari Adler.Lead began leaching into Flint’s water in 2014; the city advised residents in January 2015 that the water contained certain chemicals but was still safe to drink. The city didn’t issue a warning about lead, which is especially dangerous to children and can cause permanent developmental delays.Snyder’s popularity has plunged since the water crisis was revealed; only about 25 percent of voters gave him a favorable rating in an April 18 poll by Michigan State University. Fifty-seven percent of Michigan residents blame Snyder or an agency under his control for the water contamination, according to the poll.";
		text = "Hello \\\"world \"。 “clean” & <br> ' ; ?\"Test"; 
		/*FileUtils utils = new FileUtils();
		String translateUrl = "http://192.168.52.2:4050/translate";//utils.getPropertiesValue("config.properties","TRANSLATE_URL");
		
		String param = "{\"srcl\":\"en\",\"tgtl\":\"zh\",\"text\":\""+ text +"\"}";
	
		String result = translateByPost(translateUrl, param);
		System.out.println(result);
		//*/
		System.out.println(new Date());
		System.out.println(getTranslateFormatResult(text));
		System.out.println(new Date());
	}
	   


}
