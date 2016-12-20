package handler.news;

import edu.buaa.nlp.entity.news.ProcessedNews;
import edu.buaa.nlp.util.HtmlUtil;
import net.sf.json.JSONObject;
import news.NewsUtil;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author TW
 * @date TW on 2016/9/21.
 */
public class NewsHandlerChainV2Test {
	public static void main(String[] args) {
		String aString = "##br####br####br## ##br##";
		System.out.println(HtmlUtil.clearHTML(aString));
		long s = System.currentTimeMillis();
		ThreadPoolExecutor pool = (ThreadPoolExecutor) Executors.newCachedThreadPool();
		for (int i = 0; i < 1; i++) {
			pool.submit(new Runnable() {
				@Override
				public void run() {
					int i = 0;
					NewsHandlerChainV2 chainV2 = new NewsHandlerChainV2();
					while (i < 10000002) {
						long s = System.currentTimeMillis();
						ProcessedNews news = new ProcessedNews();
						news.setId("1");
						news.setMediaTname("新浪新闻");
						news.setTitleSrc("Two cleared of Goa beach murder of Scarlett Keeling");
						news.setPubdate("2015-06-11 12:00:00.0");
						String text = "The mother of a British teenager found dead on a beach in Goa eight years ago has vowed to fight on after the two men charged with murdering her daughter were acquitted by an Indian court today. "
								+ "\n<br>"
								+ "Scarred with dozens of injuries, the body of 15-year-old Scarlett Keeling, from Devon, was found soon after dawn on Anjuna beach in Goa on February 18, 2008."
								+ "\n<br>"
								+ "Two beach-shack workers, Samson D’Souza, 30, and Placido Carvalho, 42, were accused of plying Ms Keeling with a cocktail of drugs and alcohol before raping her and leaving her unconscious in the shallows where she drowned.…"
								+ "Scarlett’s mother, Fiona MacKeown, said she would continue to fight for justice for her daughter INDRANIL MUKHERJEE/AFP/GETTY IMAGES：\n";
						String a = "<!--type:txt--><!--type:str--> <div class=\"cpage\">#page: 1 #</div> ##br####br####br####br####br##　　9资料图。陈超 摄##br####br##　　中新网昆明10月1日电 (钱克勤)记者1日从昆明铁路局了解到，当日为国庆黄金周客流最高峰日，预计发送旅客达19.8万人次，同比增长将超过20%、突破昆明铁路局历史最高纪录。##br####br##　　9月30日，昆明铁路局迎来国庆黄金周客流出行高峰，全局发送旅客14.9万人，同比增加17.4%，其中大理丽江方向发送旅客4.3万人，同比增加39.6%。##br####br##　　从车票发售情况看，假日期间云南铁路客流主要以旅游、探亲为主，长途客流集中在北京、广州、成都等方向，短途客流以大理、丽江、河口方向为主。##br####br##　　为满足热门方向旅客出行需求，昆明铁路局在丽江、河口、宣威等方向增开列车60多趟，同时通过增设售票、改签及退票窗口，减少旅客排队等候时间；合理安排旅客购票、候车、乘降、进出站流线，为老弱病残孕等重点旅客提供帮扶服务，让旅客安全、方便、温馨出行。(完)##br##";
						news.setUrl("  aa ");
						news.setTextSrc(a);
						news.setMediaNameSrc("Sina");
						news.setLanguageCode("zh");
						news.setComeFrom("TW");
						news.setCountryNameZh("美国");
						news.setMediaLevel(1);
						try {
							chainV2.handle(news);
						} catch (Exception e) {
							e.printStackTrace();
						}
						JSONObject obj = NewsUtil.getJson(news);
						System.out.println(obj);
						System.exit(1);
						System.out.println(news.getTextSrc());
						System.out.println(System.currentTimeMillis() - s);
						i++;
					}
				}
			});
		}
		while (true) {
			System.out.println(pool.getActiveCount() + "  " + (System.currentTimeMillis() - s) + "ms");
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}