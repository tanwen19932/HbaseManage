package news.hongmaiNews.hbase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


public class HmDicUtil {
	private HmDicUtil() {
	}		
	
	public static Map<Integer,  String> getCountryMap() {
		Map<Integer,  String>  hmCountryMap = new HashMap<>();
		hmCountryMap.put(1000,"亚洲");
		hmCountryMap.put(1001,"中国");
		hmCountryMap.put(1002,"日本");
		hmCountryMap.put(1003,"韩国");
		hmCountryMap.put(1004,"印度");
		hmCountryMap.put(1005,"新加坡");
		hmCountryMap.put(1006,"泰国");
		hmCountryMap.put(1007,"马来西亚");
		hmCountryMap.put(1008,"巴基斯坦");
		hmCountryMap.put(1009,"菲律宾");
		hmCountryMap.put(1010,"越南");
		hmCountryMap.put(1011,"缅甸");
		hmCountryMap.put(1111,"澳大利亚");
		hmCountryMap.put(1211,"以色列");
		hmCountryMap.put(1311,"黎巴嫩");
		hmCountryMap.put(1411,"尼泊尔");
		hmCountryMap.put(1511,"伊朗");
		hmCountryMap.put(1611,"乌兹别克斯坦");
		hmCountryMap.put(1711,"蒙古");
		hmCountryMap.put(1811,"柬埔寨");
		hmCountryMap.put(1911,"巴林");
		hmCountryMap.put(2011,"沙特阿拉伯");
		hmCountryMap.put(2111,"印度尼西亚");
		hmCountryMap.put(2211,"斯里兰卡");
		hmCountryMap.put(2311,"也门");
		hmCountryMap.put(2411,"伊拉克");
		hmCountryMap.put(2511,"阿曼");
		hmCountryMap.put(2611,"阿联酋");
		hmCountryMap.put(2711,"阿赛拜江");
		hmCountryMap.put(2811,"孟加拉");
		hmCountryMap.put(2911,"格鲁吉亚");
		hmCountryMap.put(3011,"约旦");
		hmCountryMap.put(3111,"科威特");
		hmCountryMap.put(3211,"文莱");
		hmCountryMap.put(3311,"叙利亚");
		hmCountryMap.put(1012,"欧洲");
		hmCountryMap.put(1013,"俄罗斯");
		hmCountryMap.put(1014,"西班牙");
		hmCountryMap.put(1015,"意大利");
		hmCountryMap.put(1016,"瑞典");
		hmCountryMap.put(1018,"法国");
		hmCountryMap.put(1019,"英国");
		hmCountryMap.put(1020,"乌克兰");
		hmCountryMap.put(1021,"荷兰");
		hmCountryMap.put(1022,"德国");
		hmCountryMap.put(1023,"奥地利");
		hmCountryMap.put(1024,"白俄罗斯");
		hmCountryMap.put(1124,"土耳其");
		hmCountryMap.put(1224,"芬兰");
		hmCountryMap.put(1324,"挪威");
		hmCountryMap.put(1424,"比利时");
		hmCountryMap.put(1524,"希腊");
		hmCountryMap.put(1624,"卡塔尔");
		hmCountryMap.put(1724,"亚美尼亚");
		hmCountryMap.put(1824,"葡萄牙");
		hmCountryMap.put(1924,"罗马尼亚");
		hmCountryMap.put(2024,"爱尔兰");
		hmCountryMap.put(2124,"阿尔巴尼亚");
		hmCountryMap.put(2224,"爱沙尼亚");
		hmCountryMap.put(2324,"巴斯克");
		hmCountryMap.put(2424,"斯洛文尼亚");
		hmCountryMap.put(2524,"捷克");
		hmCountryMap.put(2624,"丹麦");
		hmCountryMap.put(2724,"斯洛伐克");
		hmCountryMap.put(2824,"波兰");
		hmCountryMap.put(2924,"立陶宛");
		hmCountryMap.put(1025,"美洲");
		hmCountryMap.put(1026,"美国");
		hmCountryMap.put(1027,"墨西哥");
		hmCountryMap.put(1028,"阿根廷");
		hmCountryMap.put(1029,"古巴");
		hmCountryMap.put(1030,"委内瑞拉");
		hmCountryMap.put(1031,"智利");
		hmCountryMap.put(1032,"多米尼加");
		hmCountryMap.put(1033,"伯利兹");
		hmCountryMap.put(1133,"加拿大");
		hmCountryMap.put(1034,"非洲");
		hmCountryMap.put(1035,"埃及");
		hmCountryMap.put(1036,"苏丹");
		hmCountryMap.put(1037,"利比亚");
		hmCountryMap.put(1038,"埃塞俄比亚");
		hmCountryMap.put(1039,"南非");
		hmCountryMap.put(1040,"利比里亚");
		hmCountryMap.put(11034,"南美洲");
		hmCountryMap.put(11035,"哥伦比亚");
		hmCountryMap.put(11036,"圭亚那");
		hmCountryMap.put(11037,"苏里南");
		hmCountryMap.put(11038,"厄瓜多尔");
		hmCountryMap.put(11039,"秘鲁");
		hmCountryMap.put(11040,"巴西");
		hmCountryMap.put(11041,"玻利维亚");
		hmCountryMap.put(11042,"巴拉圭");
		hmCountryMap.put(11043,"乌拉圭");
		hmCountryMap.put(41034,"大洋洲");
		
		return hmCountryMap;
	}
	
	public static Map<Integer,  String> getMediaTMap() {
		Map<Integer,  String>  hmMediaTMap = new HashMap<>();
		hmMediaTMap.put(100, "新闻");
		hmMediaTMap.put(200, "论坛");
		hmMediaTMap.put(1400, "博客");
		hmMediaTMap.put(400, "平媒");
		hmMediaTMap.put(600, "Twitter");
		hmMediaTMap.put(800, "SNS");
		hmMediaTMap.put(1300, "其他");
		return hmMediaTMap;
	}
	
	public static Map<Integer,  String> getLanMap() {
		Map<Integer,  String>  hmLanMap = new HashMap<>();
		hmLanMap.put(99, "中文");
		hmLanMap.put(100, "中文");
		hmLanMap.put(200, "英语");
		hmLanMap.put(300, "韩语");
		hmLanMap.put(400, "日语");
		hmLanMap.put(500, "俄语");
		hmLanMap.put(900, "西班牙语");
		hmLanMap.put(1000, "阿拉伯语");
		hmLanMap.put(1100, "葡萄牙语");
		hmLanMap.put(1200, "马来语");
		hmLanMap.put(1300, "印地语");
		hmLanMap.put(1400, "德语");
		hmLanMap.put(1500, "法语");
		hmLanMap.put(1600, "菲律宾语");
		hmLanMap.put(1700, "泰语");
		hmLanMap.put(1800, "意大利语");
		hmLanMap.put(1900, "越南语");
		hmLanMap.put(2000, "希伯来语");
		hmLanMap.put(2100, "希腊语");
		hmLanMap.put(2200, "荷兰语");
		hmLanMap.put(2300, "土耳其语");
		hmLanMap.put(2400, "芬兰语");
		hmLanMap.put(2500, "挪威语");
		hmLanMap.put(2600, "阿尔巴尼亚语");
		hmLanMap.put(2700, "马拉雅拉姆语");
		hmLanMap.put(2800, "乌兹别克斯坦语");
		hmLanMap.put(2900, "波斯语");
		hmLanMap.put(3000, "格鲁吉亚语");
		hmLanMap.put(3100, "瑞典语");
		hmLanMap.put(3200, "波兰语");
		hmLanMap.put(3300, "爱沙尼亚语");
		hmLanMap.put(3400, "阿尔巴尼亚语");
		hmLanMap.put(3500, "僧伽罗语");
		hmLanMap.put(3600, "巴斯克语");
		hmLanMap.put(3700, "蒙古语");
		hmLanMap.put(3800, "罗马尼亚语");
		hmLanMap.put(3900, "捷克语");
		hmLanMap.put(4000, "丹麦语");
		hmLanMap.put(4100, "亚美尼亚语");
		hmLanMap.put(4200, "匈牙利");
		hmLanMap.put(4300, "伊布语");
		hmLanMap.put(4400, "斯洛伐克语");
		hmLanMap.put(4500, "立陶宛语");
		hmLanMap.put(4600, "泰米尔语");
		hmLanMap.put(4700, "高棉语");
		hmLanMap.put(4800, "印地语");
		
		return hmLanMap;
	}
	

}
