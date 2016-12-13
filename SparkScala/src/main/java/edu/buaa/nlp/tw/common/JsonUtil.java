package edu.buaa.nlp.tw.common;
//
//import java.io.BufferedReader;
//import java.io.FileInputStream;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.util.ArrayList;
//import java.util.List;
//
//import com.fasterxml.jackson.databind.JavaType;
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//import edu.buaa.nlp.entity.analyse.TGeisAnalyseNCMediaTrend;
//import net.sf.json.JSONObject;//
public class JsonUtil {
//	
//	public static String getJson(String fileName) {
//        StringBuffer sb=new StringBuffer();
//       
////		InputStream in =  new JsonUtil().getClass().getResourceAsStream("/"+fileName);
//		try {
//			InputStream in =  new FileInputStream(fileName);
////			InputStream in =  new FileInputStream(new JsonUtil().getClass().getResource("/"+fileName).getFile());
//            InputStreamReader isr=new InputStreamReader(in,"utf8");                    
//            BufferedReader br=new BufferedReader(isr);    
//                
//           String line;
//            while((line=br.readLine()) != null){    
//            	sb.append(line); 
//            }    
//        } catch (Exception e) {    
//            // TODO Auto-generated catch block    
//            e.printStackTrace();    
//        }finally{        
//        	
//        }
//		return sb.toString();
//	}
//	
//	public static String arrToString(Object objArr[]){
//		String temp="";
//		if(objArr!=null){
//			for(Object obj:objArr){
//				temp+=obj+",";
//			}
//			if(temp.endsWith(",")){
//				temp=temp.substring(0,temp.length()-1);
//			}
//		}
//		return temp;
//	}
//	public static String[] stringToArr(String str){
//		String temp []= new String[]{};
//		if(str!=null){
//			 if(str.indexOf(",")>0){
//				 return str.split(",");
//			 }else{
//				 if(str!=null&&!"".equals(str)&&!"null".equals(str)){
//					 temp=new String[]{str};
//				 }
//			 }
//		}
//		return temp;
//	}
//	public static Object[] getJsonArrByKey(JSONObject jsonParam, String key) {
//		Object val[] = null;
//		try {
//			if (jsonParam == null) {
//				val = null;
//			} else {
//				if (jsonParam.containsKey(key)) {
//					val = jsonParam.getJSONArray(key).toArray();
//				} else {
//					val = null;
//				}
//			}
//		} catch (Exception e) {
//			// TODO: handle exception
//			val = null;
//		}
//		return val;
//	}
//
//	public static String getJsonValueByKey(JSONObject jsonParam, String key) {
//		String val = "";
//		try {
//			if (jsonParam == null) {
//				val = "";
//			} else {
//				if (jsonParam.containsKey(key)) {
//					val = jsonParam.getString(key);
//				} else {
//					val = "";
//				}
//			}
//		} catch (Exception e) {
//			// TODO: handle exception
//			val = "";
//		}
//		return val;
//	}
//	
//	public static <T> T jsonToObject(String jsonStr, Class<T> targetType) {
//		try {
//			return getObjectMapper().readValue(jsonStr, targetType);
//		} catch (Exception e) {
//			return null;
//		}
//	}
//	private static ObjectMapper getObjectMapper() {
//		ObjectMapper objectMapper = new ObjectMapper();
//		return objectMapper;
//	}
//	
//	public static <T> T jsonToObject(String jsonStr, Class<?> targetType, Class<?>... genericTypes) 
//	{
//		try {
//			ObjectMapper objectMapper = getObjectMapper();
//			JavaType type = objectMapper.getTypeFactory()
//									.constructParametricType(targetType, genericTypes);
//			return objectMapper.readValue(jsonStr, type);
//		} catch (Exception e) {
//			return null;
//		}
//	}
//	
//	public static void main(String[] args) {
//		String test="[{\"date\":\"2016-07-17\",\"results\":{\"中国\":{\"合肥新闻频道-首页\":1}}},{\"date\":\"2016-07-18\",\"results\":{\"中国\":{\"中国新闻网-兵团_法制新闻\":1,\"热点新闻-首页\":1,\"大数据观察网-首页\":1}}},{\"date\":\"2016-07-19\",\"results\":{\"中国\":{\"佛山市保险行业协会-首页\":1}}},{\"date\":\"2016-07-20\",\"results\":{}},{\"date\":\"2016-07-21\",\"results\":{}},{\"date\":\"2016-07-22\",\"results\":{\"中国\":{\"日照新闻网-新闻\":1,\"多米网-首页\":1}}},{\"date\":\"2016-07-23\",\"results\":{}},{\"date\":\"2016-07-24\",\"results\":{\"中国\":{\"佛山市保险行业协会-首页\":1,\"字母表新闻网-首页\":1,\"正大文明网-首页\":1,\"阜阳IT网-首页\":1}}},{\"date\":\"2016-07-25\",\"results\":{}},{\"date\":\"2016-07-26\",\"results\":{}},{\"date\":\"2016-07-27\",\"results\":{\"中国\":{\"法治中国新闻网--国内新闻\":1,\"连云港新闻网-银行\":1,\"中国新闻网-广东_高官\":1,\"合肥新闻频道-首页\":1}}},{\"date\":\"2016-07-28\",\"results\":{\"中国\":{\"创智网-首页\":2,\"搜我们-果粉频道\":1}}},{\"date\":\"2016-07-29\",\"results\":{}},{\"date\":\"2016-07-30\",\"results\":{}},{\"date\":\"2016-07-31\",\"results\":{\"中国\":{\"中新网-首页\":1}}},{\"date\":\"2016-08-01\",\"results\":{\"中国\":{\"合作新闻网-社会\":1}}},{\"date\":\"2016-08-02\",\"results\":{\"中国\":{\"头条科技-首页\":1,\"东方财富网股吧-(300104)乐视网\":1,\"丹阳网-首页\":1,\"Xinjr\":1}}},{\"date\":\"2016-08-03\",\"results\":{\"中国\":{\"新浪博客\":1}}},{\"date\":\"2016-08-04\",\"results\":{\"中国\":{\"笃学好古新闻网-财经\":1,\"笃学好古新闻网-社会\":5,\"中国三农吉讯网-资讯\":1,\"太湖明珠网-国内\":1,\"太湖明珠网-社会\":8,\"太湖明珠网-IT数码\":1,\"笃学好古新闻网-国内\":1}}},{\"date\":\"2016-08-05\",\"results\":{\"中国\":{}}},{\"date\":\"2016-08-06\",\"results\":{\"中国\":{\"渭源都市在线-社会热点\":1}}},{\"date\":\"2016-08-07\",\"results\":{\"中国\":{\"凤凰博客\":1,\"武汉新闻网-首页\":1,\"中山市人民医院-首页\":1}}},{\"date\":\"2016-08-08\",\"results\":{\"中国\":{\"中西部法制期刊-法治\":1,\"重庆市政府网-首页\":3,\"中国新媒体网-建筑类\":1,\"和平论坛\":1,\"知历史-近代史\":1,\"肯追网-热点\":1,\"新浪网\":1,\"重庆市政府网-社会\":5}}},{\"date\":\"2016-08-09\",\"results\":{\"中国\":{\"白银市公安局-首页\":1,\"盘锦都市在线-社会热点\":1,\"利剑网-新闻中心\":1,\"市长杂志网-首页\":1,\"剑客-创投\":1,\"b44ct.wpbxx-实时\":1}}},{\"date\":\"2016-08-10\",\"results\":{\"中国\":{\"新港发展网-热点关注\":1,\"旅游产业博览会-娱乐\":1,\"七八楼最热新闻网-娱乐\":1,\"Huaxia\":1,\"热点新闻网-娱乐\":1,\"部队赴福建泰宁山体滑坡灾害现场抢险救援新闻网-人物\":1,\"乌海最新热点新闻网-娱乐\":1,\"时时新闻网-娱乐\":1}}},{\"date\":\"2016-08-11\",\"results\":{\"中国\":{\"360doc.com\":1,\"JF Daily\":1,\"Workercn\":1,\"Mirror (CN)\":1}}},{\"date\":\"2016-08-12\",\"results\":{\"中国\":{\"Sichuan New Net\":1,\"说三道四-未知探索\":1,\"中国IDC服务网-首页\":1,\"Legal daily\":1,\"80end (cn)\":1,\"CCIDNET\":1,\"新港发展网-滚动新闻\":1,\"GYWB\":1,\"Xinjr\":1,\"People' Daily Online\":1}}},{\"date\":\"2016-08-13\",\"results\":{\"中国\":{\"QQ \":1,\"Chinaen\":1,\"Shengyidi\":1,\"China Network Information Sytem\":1,\"Difang\":1,\"Taihai Net\":1,\"Workercn\":2,\"Haonipai\":1,\"微信人生-旅游\":1}}},{\"date\":\"2016-08-14\",\"results\":{\"中国\":{\"9q9la.xbpjx-瞭望\":1,\"Chinaen\":1,\"Dian 321\":1,\"cnBeta\":1,\"51 camel\":1,\"Zol.com\":1,\"China rednet\":1,\"Dahejz\":1,\"Workercn\":1,\"Sohu\":1},\"新加坡\":{\"中国驻新加坡共和国大使馆\":1}}},{\"date\":\"2016-08-15\",\"results\":{\"中国\":{\"xdmpx-讲坛\":1,\"China-3G\":1,\"Haonipai\":1,\"Sohu\":1,\"Information Time\":1}}},{\"date\":\"2016-08-16\",\"results\":{\"中国\":{\"Capital Market\":1,\"Hangzhou New\":1,\"Hexun\":1,\"MyDriver.com\":1,\"Dian 321\":2,\"Dayday New\":1,\"PConline\":1,\"Mirror (CN)\":2,\"163.com\":1,\"Haonipai\":1}}},{\"date\":\"2016-08-17\",\"results\":{\"中国\":{\"Dayoo\":2,\"Capital Market\":1,\"China Economic\":1,\"SD New\":1,\"CJN\":1,\"China Network Information Sytem\":2,\"51 camel\":1,\"JZNew\":1,\"Hebei\":1,\"Information Time\":1}}},{\"date\":\"2016-08-18\",\"results\":{\"中国\":{\"Beijing New (CN)\":1,\"Chinaen\":2,\"24Inf\":1,\"CNR\":1,\"JF Daily\":2,\"China Network Information Sytem\":1,\"CnFOL\":1,\"21CN\":2,\"Sohu\":3,\"New QTV\":2}}},{\"date\":\"2016-08-19\",\"results\":{\"中国\":{\"Gmw\":1,\"Guangyuanol.cn\":1,\"Chinaen\":1,\"Sonhoo\":1,\"Ifeng\":1,\"Syd (CN)\":1,\"Dahejz\":1,\"Dayday New\":1,\"Haonipai\":1,\"New Tuxi\":2}}},{\"date\":\"2016-08-20\",\"results\":{\"中国\":{\"Xinmin\":1,\"金鹿在线-财经\":1,\"jiaodong.net\":1,\"New Tuxi\":2}}},{\"date\":\"2016-08-21\",\"results\":{\"中国\":{\"Xinhuanet\":1,\"Syd (CN)\":1,\"Sohu\":1,\"wyppx-讲坛\":1}}},{\"date\":\"2016-08-22\",\"results\":{\"中国\":{\"Gmw\":1,\"China Economic\":2,\"中国股票-首页\":8,\"wpyx-专题\":1,\"24Inf\":1,\"E North Net\":1,\"51CTO (cn)\":1,\"2rdq4.wpwkx-综合\":1,\"C114\":1,\"kq9t0.pin827要闻\":1}}},{\"date\":\"2016-08-23\",\"results\":{\"中国\":{\"Inhe\":1,\"Science net\":1,\"Caijing\":1,\"Wpphx-论谈\":1,\"Syd (CN)\":1,\"It.0931lanzhou\":1,\"C114\":1,\"kq9t0.pin827讲坛\":1,\"Eatday (cn)\":2,\"Jinling Online\":1},\"加拿大\":{\"Info 51\":1}}},{\"date\":\"2016-08-24\",\"results\":{\"中国\":{\"Beijing New (CN)\":1,\"Ifeng\":4,\"ChinaNew\":2,\"24Inf\":1,\"Chengdu\":1,\"China Network Information Sytem\":1,\"China.com\":1,\"163.com\":3,\"Beijing Buine Today\":1,\"Admin 5\":1}}},{\"date\":\"2016-08-25\",\"results\":{\"美国\":{\"ptxw.com\":1},\"中国\":{\"Gmw\":6,\"Xinmin\":5,\"QQ \":4,\"24Inf\":3,\"CNR\":3,\"M CNR\":4,\"China Network Information Sytem\":3,\"China.com\":3,\"Haonipai\":5,\"Sohu\":10}}},{\"date\":\"2016-08-26\",\"results\":{\"中国\":{\"Xinmin\":17,\"Gmw\":6,\"Ifeng\":5,\"24Inf\":4,\"ChinaNew\":4,\"China Network Information Sytem\":5,\"GYWB\":6,\"163.com\":4,\"Sohu\":16,\"Chongqing New Net\":5},\"加拿大\":{\"Info 51\":2}}},{\"date\":\"2016-08-27\",\"results\":{\"中国\":{\"Xinmin\":19,\"Gmw\":9,\"Beijing New (CN)\":4,\"24Inf\":6,\"China Network Information Sytem\":8,\"Difang\":8,\"China.com\":4,\"Beijing huayue\":4,\"163.com\":4,\"Sohu\":21},\"加拿大\":{\"Info 51\":2}}},{\"date\":\"2016-08-28\",\"results\":{\"中国\":{\"Gmw\":9,\"Xinmin\":7,\"Xinhuanet\":3,\"Ifeng\":3,\"24Inf\":5,\"JF Daily\":3,\"China Network Information Sytem\":6,\"Dahejz\":3,\"163.com\":4,\"Sohu\":15},\"加拿大\":{\"Info 51\":2}}},{\"date\":\"2016-08-29\",\"results\":{\"中国\":{\"Beijing New (CN)\":3,\"QQ \":3,\"Hexun\":3,\"Ifeng\":7,\"China Network Information Sytem\":3,\"M CNR\":3,\"51 camel\":2,\"163.com\":11,\"Sohu\":7,\"Eatday (cn)\":5}}},{\"date\":\"2016-08-30\",\"results\":{\"中国\":{\"Xinmin\":6,\"Gmw\":3,\"China Economic\":2,\"Ifeng\":11,\"ChinaNew\":2,\"China Network Information Sytem\":2,\"Workercn\":3,\"163.com\":7,\"Sohu\":10,\"Eatday\":3},\"加拿大\":{\"Info 51\":1}}},{\"date\":\"2016-08-31\",\"results\":{\"美国\":{\"Chinapre\":1,\"ptxw.com\":1},\"中国\":{\"Xinmin\":8,\"Beijing New (CN)\":5,\"CWW\":4,\"Dazhong Net\":4,\"Ifeng\":5,\"China Network Information Sytem\":4,\"Yunjuu\":6,\"Haonipai\":9,\"Sohu\":9,\"New Tuxi\":8},\"加拿大\":{\"Info 51\":2}}},{\"date\":\"2016-09-01\",\"results\":{\"美国\":{\"ptxw.com\":1},\"智利\":{},\"中国\":{\"Xinmin\":11,\"Gmw\":6,\"Beijing New (CN)\":4,\"Xinhuanet\":4,\"JF Daily\":4,\"163.com\":6,\"Haonipai\":4,\"Sohu\":8,\"C114\":4,\"New Tuxi\":4},\"加拿大\":{\"Info 51\":1}}},{\"date\":\"2016-09-02\",\"results\":{\"中国\":{\"Beijing New (CN)\":2,\"Gmw\":2,\"Xinmin\":2,\"Ifeng\":4,\"China Buine\":4,\"Anhui New\":2,\"PConline\":3,\"ChinaGate\":2,\"Haonipai\":2,\"Sohu\":2},\"加拿大\":{\"Info 51\":2}}},{\"date\":\"2016-09-03\",\"results\":{\"马来西亚\":{\"Seehua\":1},\"中国\":{\"0898\":1,\"Ifeng\":2,\"Bond.cfi\":2,\"24Inf\":1,\"JF Daily\":2,\"Yunjuu\":3,\"Beijing huayue\":2,\"Farmer\":3,\"Haonipai\":2,\"Sohu\":4}}},{\"date\":\"2016-09-04\",\"results\":{\"中国\":{\"24Inf\":1,\"6k0ee.wpkwx实时\":1,\"Difang\":2,\"Sina (CN)\":2,\"Beijing huayue\":2,\"Yunjuu\":2,\"新南京-法治\":2,\"163.com\":2,\"Haonipai\":2,\"National Buine Daily\":2},\"加拿大\":{\"Info 51\":1}}},{\"date\":\"2016-09-05\",\"results\":{\"智利\":{},\"中国\":{\"China Daily\":2,\"JF Daily\":2,\"jjmmw.com\":2,\"CnFOL\":4,\"Haonipai\":3,\"163.com\":1,\"New Tuxi\":4,\"Beijing Buine Today\":3,\"Sohu\":2,\"Information Time\":2},\"加拿大\":{\"Info 51\":1}}},{\"date\":\"2016-09-06\",\"results\":{\"中国\":{\"Gmw\":5,\"Beijing New (CN)\":2,\"Ccidcom\":2,\"China Economic\":2,\"China Network Information Sytem\":2,\"CnFOL\":2,\"ChinaZ\":2,\"163.com\":4,\"Sohu\":3,\"Jinling Online\":3}}},{\"date\":\"2016-09-07\",\"results\":{\"美国\":{},\"智利\":{},\"中国\":{\"Gmw\":10,\"Xinmin\":5,\"ChinaNew\":5,\"24Inf\":4,\"JF Daily\":6,\"China Network Information Sytem\":7,\"China.com\":4,\"Yangte\":5,\"Workercn\":6,\"Sohu\":5},\"加拿大\":{\"Info 51\":1}}},{\"date\":\"2016-09-08\",\"results\":{\"智利\":{},\"中国\":{\"Gmw\":7,\"Xinmin\":5,\"QQ \":5,\"China Network Information Sytem\":9,\"Zol.com\":3,\"Farmer\":3,\"21CN\":3,\"Sohu\":4,\"New Tuxi\":3,\"Information Time\":5},\"新加坡\":{\"Capital 95.8 FM\":1}}},{\"date\":\"2016-09-09\",\"results\":{\"美国\":{\"ptxw.com\":1},\"香港\":{},\"中国\":{\"Xinmin\":4,\"Capital Market\":2,\"ChinaNew\":2,\"China Network Information Sytem\":5,\"51 camel\":2,\"Mzz\":3,\"Haonipai\":7,\"163.com\":2,\"New Tuxi\":5,\"Sohu\":5}}},{\"date\":\"2016-09-10\",\"results\":{\"美国\":{\"中新网\":1},\"智利\":{},\"中国\":{\"Gmw\":2,\"24Inf\":2,\"China Network Information Sytem\":2,\"China.com\":4,\"CnFOL\":2,\"163.com\":2,\"Dahe \":2,\"Haonipai\":2,\"New Tuxi\":8,\"Eatday\":2},\"日本\":{\"体育信息聚合-科技\":1}}},{\"date\":\"2016-09-11\",\"results\":{\"美国\":{},\"亚洲\":{\"余姚新闻网-首页\":1},\"中国\":{\"hiqitong.com今日头条-首页\":5,\"热点新闻网-首页\":84,\"热点资讯网-首页\":19,\"euoaeoiu.bjxtb.com-首页\":5,\"boota.com.cn今日头条-首页\":5,\"热点新闻网-IT数码\":10,\"lzfcy.com热点新闻网-首页\":5,\"ihome1882.com热点资讯网-首页\":5,\"rc635.com热点新闻网-首页\":5},\"日本\":{\"体育信息聚合-科技\":1,\"体育信息聚合-健康\":3,\"体育信息聚合-体育\":1}}},{\"date\":\"2016-09-12\",\"results\":{\"美国\":{},\"亚洲\":{\"第一经济网-理财\":1},\"中国\":{\"兴充新闻网-首页\":9,\"wzpwx-校报\":6,\"热点新闻网-首页\":20,\"万扶新闻网-首页\":6,\"湖丽新闻网-首页\":10,\"梅长新闻网-首页\":8,\"城港新闻网-首页\":8,\"福福新闻网-首页\":7,\"滁头新闻网-首页\":14},\"日本\":{\"体育信息聚合-教育\":1,\"资阳网-文艺\":1,\"体育信息聚合-健康\":1}}},{\"date\":\"2016-09-13\",\"results\":{\"亚洲\":{\"余姚新闻网-首页\":1},\"智利\":{},\"中国\":{\"2668股票网-首页\":7,\"热点新闻网-首页\":23,\"Xcpcx-人物\":7,\"tlc289-人物\":6,\"bho8.xjpmx-视点\":8,\"WPSKX-掠影\":7,\"xhtpx-掠影\":6,\"boota.com.cn今日头条-首页\":5,\"guwlz.wpbx-专题\":5},\"日本\":{\"体育信息聚合-科技\":3,\"体育信息聚合-健康\":2,\"上海新闻网-体育\":1}}},{\"date\":\"2016-09-14\",\"results\":{\"中国\":{\"c9t0b.xjwpx-媒体\":6,\"WPSKX-专题\":5,\"ywh19m.xdpbx-瞭望\":6,\"WYPWX-视点\":6,\"TLC-综合\":5,\"wpmzx-掠影\":6,\"WYPWX-媒体\":6,\"rb88h-媒体\":6,\"6wt0i.hjk88-人物\":5},\"其他\":{\"费伦财经资讯网-首页\":1},\"日本\":{\"体育信息聚合-科技\":3,\"体育信息聚合-健康\":1,\"体育信息聚合-体育\":1,\"中国IDC服务网-新闻\":1}}},{\"date\":\"2016-09-15\",\"results\":{\"中国\":{\"f2xeq.wpbpx-实时\":6,\"1yhjk.xfjpx-时评\":5,\"c9t0b.xjwpx-瞭望\":5,\"WPBKX-讲坛\":5,\"wzgpx-媒体\":6,\"tlc213-校报\":6,\"Xjkpx-综合\":5,\"中国航空新闻网-首页\":7,\"Wppjx-时评\":5}}},{\"date\":\"2016-09-16\",\"results\":{\"中国\":{\"6wt0i.hjk88-实时\":6,\"adf88-时评\":5,\"Xjpbx-视点\":6,\"58uef.wphzx.cn-人物\":5,\"Wpdtx-讲坛\":5,\"WPSKX-讲坛\":6,\"wpdzx-专题\":7,\"Wpdtx-首页\":7,\"5nvp7.61474694x-首页\":5}}},{\"date\":\"2016-09-17\",\"results\":{\"中国\":{\"9u7z0.wpbzx-要闻\":4,\"WPSKX-专题\":4,\"rb88h-视点\":5,\"蓝黑主席已点头新闻网-实时\":5,\"guwlz.wpbx-媒体\":5,\"prmub.xdppx-瞭望\":5,\"wphdx-瞭望\":5,\"Wympx-掠影\":4,\"tlc289-掠影\":5},\"日本\":{\"体育信息聚合-健康\":1}}},{\"date\":\"2016-09-18\",\"results\":{\"中国\":{\"中国新闻网-黑龙江_爆料\":1,\"3023-首页\":1,\"东莞新闻网-首页\":1}}},{\"date\":\"2016-09-19\",\"results\":{\"中国\":{\"永康新闻网-首页\":1,\"ITBear科技资讯-移动互联\":1,\"中国新闻网-湖南_热点\":1,\"百度贴吧-捷信金融吧\":1}}},{\"date\":\"2016-09-20\",\"results\":{\"中国\":{\"中工网-首页\":1,\"安徽省公安厅-首页\":1,\"中国网络空间安全网-特别策划\":1,\"川财证券-首页\":1,\"优度娱乐网-首页\":1,\"库尔勒新闻网-热点评论\":1,\"安徽公安网-首页\":1,\"河北企业网-首页\":1,\"中国新闻网-海南_综合播报\":1}}},{\"date\":\"2016-09-21\",\"results\":{\"智利\":{},\"中国\":{\"大四川\":2,\"准二胎网-首页\":1,\"中国新闻网-云南_财经新闻\":1,\"Law.cyol\":1,\"xw1806.com\":2,\"jjmmw.com\":1,\"Law South CN\":2,\"chinaqking.com\":1,\"新蓝网\":2},\"新加坡\":{}}},{\"date\":\"2016-09-22\",\"results\":{\"中国\":{\"中国新闻网-云南_财经新闻\":1,\"大众网-临沂\":1,\"国际在线-山东_平安山东\":1,\"朝阳新闻网-汽车\":1,\"中新网-首页\":1,\"大连门户网-首页\":1,\"全球经济热点-通信\":1,\"365bet-365bet\":1,\"卓安律师事务所-刑事动态\":1}}},{\"date\":\"2016-09-23\",\"results\":{\"中国\":{\"China Economic\":1,\"China Securitie Journal Net\":1,\"Difang\":1,\"CNSN\":1,\"China New\":1,\"CnFOL\":1,\"Ditrict\":1,\"163.com\":1,\"CNWet\":1}}},{\"date\":\"2016-09-24\",\"results\":{\"美国\":{\"长城网\":1},\"中国\":{\"Gmw\":1,\"苏州资讯-社会民生\":1,\"望京网论坛\":1,\"360doc.com\":1,\"中国八闽家园官方网站-首页\":1,\"chaozhoudaily.com\":1,\"大连新闻网\":1,\"中国新闻网-青海_国内国际\":1,\"柳州新闻视频第一门户-新闻\":1}}},{\"date\":\"2016-09-25\",\"results\":{\"中国\":{\"大四川\":2,\"xw1806.com\":3,\"五星旅游网-生活服务\":1,\"剑客-科技\":1,\"chinaqking.com\":1,\"Gznet\":1,\"3023-首页\":1,\"全球经济热点-通信\":1,\"cyol.com\":1}}},{\"date\":\"2016-09-26\",\"results\":{\"中国\":{\"平谷论坛-平安平谷\":1,\"kejixun.com\":1,\"中国中小企业鞍山网-产经\":1,\"克拉玛依新闻网-房产\":1,\"国际在线-山东_滚动新闻\":1,\"中山三乡-信息公开\":1,\"中文商业新闻网\":1,\"天下财经网-科技\":1,\" 环球财经教育在线-考试\":1}}},{\"date\":\"2016-09-27\",\"results\":{\"中国\":{\"中国审判-首页\":1,\"好站长-首页\":1,\"江苏卫视\":1,\"chaozhoudaily.com\":1,\"腾讯-财经新闻-股票-股票要闻\":1,\"汇投网-首页\":1,\"3023-首页\":1,\"川南经济网-经济\":1}}},{\"date\":\"2016-09-28\",\"results\":{\"美国\":{\"ptxw.com\":1},\"中国\":{\"Ab cbn\":1,\"Ccidcom\":3,\"258\":1,\"CJN\":1,\"China Network Information Sytem\":1,\"CNW New\":1,\"Workercn\":2,\"3023-首页\":1,\"New Tuxi\":7}}},{\"date\":\"2016-09-29\",\"results\":{\"美国\":{\"ptxw.com\":1},\"智利\":{\"CDYEE\":1},\"中国\":{\"Gmw\":16,\"Xinmin\":9,\"80end (cn)\":3,\"China Network Information Sytem\":7,\"Difang\":3,\"Shehui China\":3,\"Workercn\":3,\"New Tuxi\":11,\"Sohu\":3,\"齐鲁晚报电子版\":4}}},{\"date\":\"2016-09-30\",\"results\":{\"中国\":{\"Gmw\":6,\"Xinmin\":6,\"Toutiao\":4,\"Xinhuanet\":4,\"ChinaNew\":4,\"China Network Information Sytem\":10,\"CnFOL\":5,\"163.com\":7,\"New Tuxi\":10,\"Sohu\":4},\"日本\":{\"体育信息聚合-科技\":2}}},{\"date\":\"2016-10-01\",\"results\":{\"中国\":{\"Xinmin\":7,\"Gmw\":3,\"安罗新闻网-首页\":4,\"渤海新区官方网站-首页\":25,\"China Network Information Sytem\":4,\"中国消费资讯网-首页\":4,\"wpchx-综合\":3,\"Haonipai\":3,\"New Tuxi\":6,\"腾讯-新闻-社会新闻\":5},\"日本\":{\"体育信息聚合-房产\":1,\"体育信息聚合-娱乐\":1}}},{\"date\":\"2016-10-02\",\"results\":{\"中国\":{\"化州唯一的新闻门户网-首页\":2,\"游戏梦网-首页\":2,\"永州头条-首页\":2,\"中国消费资讯网-首页\":2,\"商洛传媒网-科技\":2,\"中新网-首页\":2,\"全球资讯网-首页\":2,\"微创资讯网-首页\":2,\"光明网-科技频道前沿动态\":2,\"天涯社区-天涯杂谈\":2}}},{\"date\":\"2016-10-03\",\"results\":{\"中国\":{\"平谷论坛-平安平谷\":2,\"时代站长-首页\":2,\"条目网-科技\":3,\"安北网-首页\":3,\"中国消费资讯网-首页\":3,\"原油黄金外汇投资分析网-首页\":2,\"很哈网-首页\":2,\"条目网-财经\":2,\"民生360-首页\":4}}},{\"date\":\"2016-10-04\",\"results\":{\"中国\":{\"求医网-首页\":2,\"贼吧网-首页\":2,\"华南网-首页\":2,\"中国消费资讯网-首页\":4,\"有道-互联网新闻\":2,\"IT168-手机\":2,\"中新网-首页\":2,\"CnFOL\":2,\"微热文-资讯\":2}}},{\"date\":\"2016-10-05\",\"results\":{\"中国\":{\"创智网-IT要问\":2,\"宝码网-科技\":2,\"中华财富网-金融资本\":2,\"中国消费资讯网-首页\":6,\"wpchx-综合\":3,\"微热文-首页\":2,\"广西在线-首页\":3,\"中新网-首页\":2,\"天涯社区-天涯杂谈\":2}}},{\"date\":\"2016-10-06\",\"results\":{\"香港\":{},\"中国\":{\"中国网-滚动\":2,\"三思教育网-首页\":7,\"头条科技-首页\":10,\"新华网-RSS订阅中心\":2,\"中国消费资讯网-首页\":4,\"wpchx-综合\":2,\"中国服装设计网-首页\":2,\"天涯社区-了望天涯\":2,\"新疆网-新疆\":2},\"加拿大\":{},\"日本\":{\"体育信息聚合-科技\":1}}},{\"date\":\"2016-10-07\",\"results\":{\"中国\":{\"人民法院报\":2,\"一如新闻网-首页\":2,\"中国消费资讯网-首页\":5,\"C114中国通信-滚动\":2,\"wpchx-综合\":2,\"Cicnn\":4,\"搜吧-首页\":2,\"携景财富-财经\":2,\"娱乐圈新闻-资讯\":2},\"日本\":{\"Vita之家-首页\":1,\"体育信息聚合-互联网\":1}}},{\"date\":\"2016-10-08\",\"results\":{\"中国\":{\"东日新闻网-财经\":2,\"和讯-财经新闻-消费\":2,\"大铁棍子-首页\":2,\"兰州新闻网-首页\":2,\"天涯社区-海南\":2,\"中国教育和科研计算机网\":2,\"正义网-社会\":2,\"今日头条-理财\":2,\"今日头条-财经\":2,\"21CN-首页\":2}}},{\"date\":\"2016-10-09\",\"results\":{\"中国\":{\"江民论坛-动态新闻交流区\":2,\"中国农业新闻-首页\":1,\"中国保险网-保险时讯\":1,\"三姨看天下-首页\":1,\"广电电器网-学习园地\":2,\"5956技术网-首页\":1,\"3023-首页\":1,\"Ecn.cn\":1,\"中华在线-科技\":1,\"东莞新闻网-首页\":3}}},{\"date\":\"2016-10-10\",\"results\":{\"美国\":{\"1979.com\":1,\"CHINAdaily\":1},\"中国\":{\"Xinmin\":8,\"山东2哥网-首页\":51,\"最全的热门新闻排行榜-首页\":5,\"爱江西信息网-首页\":11,\"滕州全媒体-最新\":7,\"企贸网-新闻资讯\":6,\"中国消费资讯网-首页\":5,\"China Network Information Sytem\":4,\"企贸网-首页\":9,\"New Tuxi\":6}}},{\"date\":\"2016-10-11\",\"results\":{\"亚洲\":{\"江苏省检察网-要闻\":1},\"智利\":{\"CDYEE\":1},\"中国\":{\"Xinmin\":8,\"Gmw\":7,\"山东2哥网-首页\":33,\"深港在线-便民-社会百态\":5,\"爱江西信息网-首页\":5,\"中国消费资讯网-首页\":13,\"现采新闻网-娱乐\":10,\"China Network Information Sytem\":8,\"中新网-首页\":14,\"New Tuxi\":15},\"其他\":{\"搜狐-社会新闻-社会要闻-世态万象\":1},\"日本\":{\"体育信息聚合-教育\":1,\"体育信息聚合-游戏\":1,\"凯迪社区-海口会馆\":1}}},{\"date\":\"2016-10-12\",\"results\":{\"美国\":{\"1979.com\":1},\"中国\":{\"Gmw\":8,\"Xinmin\":4,\"零度网-首页\":7,\"上海资讯网-环保\":4,\"中国消费资讯网-首页\":6,\"China Network Information Sytem\":4,\"中新网-首页\":5,\"Beelink \":4,\"Haonipai\":6,\"New Tuxi\":5},\"其他\":{\"新浪-科技首页-电信\":1},\"日本\":{\"体育信息聚合-科技\":1}}},{\"date\":\"2016-10-13\",\"results\":{\"中国\":{\"Xinmin\":8,\"Gmw\":6,\"ChinaNew\":4,\"一带一路城市网-网络安全\":6,\"JF Daily\":4,\"中国消费资讯网-首页\":12,\"China Network Information Sytem\":4,\"wpchx-综合\":4,\"中新网-首页\":6,\"New Tuxi\":4},\"日本\":{\"体育信息聚合-科技\":1}}},{\"date\":\"2016-10-14\",\"results\":{\"美国\":{\"ptxw.com\":1},\"中国\":{\"Gmw\":9,\"Xinmin\":5,\"China Network Information Sytem\":9,\"中国消费资讯网-首页\":6,\"qianhuaweb.com\":5,\"中新网-首页\":9,\"ahradio.com.cn\":4,\"Workercn\":5,\"Farmer\":3,\"New Tuxi\":10},\"日本\":{\"体育信息聚合-汽车\":1}}},{\"date\":\"2016-10-15\",\"results\":{\"中国\":{\"Gmw\":2,\"中国股票-首页\":2,\"中国长安网-高层\":2,\"一带一路城市网-网络安全\":4,\"中国消费资讯网-首页\":4,\"中新网-首页\":5,\"一带一路城市网-首页\":3,\"Dahe \":3,\"摇篮网育儿论坛-婚姻家庭\":3,\"New Tuxi\":2},\"日本\":{\"凯迪社区-舆情观察\":1,\"体育信息聚合-教育\":1}}},{\"date\":\"2016-10-16\",\"results\":{\"中国\":{\"Xinhuanet\":3,\"河北英语体育新闻-城市\":58,\"一带一路城市网-网络安全\":3,\"中国消费资讯网-首页\":12,\"China Network Information Sytem\":2,\"中新网-首页\":3,\"一带一路城市网-首页\":2,\"New Tuxi\":2,\"Sohu\":2,\"112 eo\":2},\"日本\":{\"体育信息聚合-教育\":1}}},{\"date\":\"2016-10-17\",\"results\":{\"智利\":{\"CDYEE\":1},\"中国\":{\"Gmw\":5,\"山东2哥网-首页\":38,\"Huanqiu\":3,\"中国消费资讯网-首页\":9,\"China Network Information Sytem\":4,\"中新网-首页\":11,\"Cnii\":4,\"CnFOL\":3,\"中国航空新闻网-首页\":6,\"New Tuxi\":4},\"日本\":{\"体育信息聚合-科技\":2}}},{\"date\":\"2016-10-18\",\"results\":{\"亚洲\":{\"余姚新闻网-首页\":1},\"中国\":{\"3533手机网-资讯首页\":1,\"华讯网-首页\":2,\"中国消费资讯网-首页\":2,\"21CN-生活\":1,\"中新网-首页\":5,\"中华新闻网-首页\":2,\"365在线-首页\":1,\"门头沟论坛\":3,\"泉州新闻网-首页\":2,\"云浮新闻网-首页\":2}}},{\"date\":\"2016-10-19\",\"results\":{\"中国\":{\"咸宁新闻网-文化\":1,\"中国创业联盟网-首页\":1,\"金山党务-政法工作\":1,\"中国创业联盟-首页\":2,\"中原石油新闻网-首页\":1,\"chinaqking.com\":1,\"中国人民共和国国务院新闻办公室-首页\":1,\"鄂尔多斯市道路运输管理局-运政新闻\":1,\"中国新闻网-重庆_社会\":1}}},{\"date\":\"2016-10-20\",\"results\":{\"中国\":{\"五星旅游网-经济民生\":1,\"中国江门网-首页\":1,\"原达新闻-首页\":1,\"国际在线-福建_金融\":1,\"360doc个人图书馆-首页\":1,\"大冶教育信息网-首页\":1,\"大江晚报\":1,\"众视-融合通信\":1,\"中新网\":1,\"中国行业信息网-行业资讯\":1}}},{\"date\":\"2016-10-21\",\"results\":{\"中国\":{\"xfwpx-实时\":1,\"xfwpx-视点\":2,\"一如新闻网-首页\":2,\"kone平台注册-首页\":1,\"xfwpx-校报\":1,\"百度新闻订阅-社会最新\":2,\"中新网-首页\":4,\"xfwpx-掠影\":1,\"xfwpx-人物\":2}}},{\"date\":\"2016-10-22\",\"results\":{\"亚洲\":{\"金星炮轰《非常搭档》-首页\":1},\"中国\":{\"河北新闻网-河北\":2,\"俩族网-首页\":1,\"xfwpx-视点\":1,\"xfwpx-首页\":1,\"中国学网-软件教室\":1,\"xfwpx-讲坛\":1,\"中新网-首页\":1,\"xfwpx-媒体\":1,\"永长新闻网-首页\":4,\"中国新闻网-辽宁_教育\":1}}},{\"date\":\"2016-10-23\",\"results\":{\"中国\":{\"企业信用网-首页\":2,\"中国农金网 -首页\":1,\"读选网-气质范\":2,\"Lemge搜索-国内\":1,\"三湘综治网-首页\":1,\"贴囧网-首页\":2,\"中国创客网-人工智能\":1,\"火狐中文网-首页\":2,\"中国企业社会责任监测和评价系统-首页\":1}}},{\"date\":\"2016-10-24\",\"results\":{\"美国\":{\"zdnet\":1},\"中国\":{\"河北新闻网-河北\":11,\"法制晚报电子版\":3,\"微创资讯网-最新手机\":3,\"光明网-科技滚动读报\":2,\"百度新闻订阅-社会最新\":3,\"河北新闻网-财经\":8,\"中国警察网-大案要案\":2,\"河北新闻网-原创\":3,\"云浮新闻网-首页\":2},\"其他\":{\"费伦财经资讯网-首页\":2},\"日本\":{\"Vita之家-首页\":1,\"体育信息聚合-互联网\":1}}},{\"date\":\"2016-10-25\",\"results\":{\"中国\":{\"安罗新闻网-首页\":4,\"xfwpx-专题\":4,\"百度新闻订阅-保定新闻\":3,\"南国早报\":3,\"条目网-首页\":4,\"梅长新闻网-首页\":4,\"百度新闻订阅-唐山新闻\":3,\"xfwpx-媒体\":5,\"12377.cn\":3},\"日本\":{\"体育信息聚合-科技\":1,\"凯迪社区-律师之窗\":1,\"中国IDC服务网-新闻\":1}}},{\"date\":\"2016-10-26\",\"results\":{\"中国\":{\"中国西部新闻网-首页\":2,\"新华网-社会\":3,\"明星资讯网-影视\":2,\"xfwpx-专题\":3,\"xfwpx-媒体\":3,\"中青在线-首页\":2,\"安徽法制报-首页\":2,\"投资中国网-宏观\":3,\"xfwpx-人物\":4},\"日本\":{\"体育信息聚合-科技\":1}}},{\"date\":\"2016-10-27\",\"results\":{\"中国\":{\"酷我网-首页\":3,\"中国私人理财网-首页\":4,\"xfwpx-视点\":3,\"xfwpx-专题\":2,\"wpchx-综合\":2,\"xfwpx-掠影\":4,\"xfwpx-视频\":4,\"xfwpx-媒体\":3,\"光明网-产品图解\":3}}},{\"date\":\"2016-10-28\",\"results\":{\"美国\":{},\"智利\":{},\"中国\":{\"百度贴吧-北镇吧\":3,\"中国经济网-新闻\":3,\"xfwpx-论谈\":4,\"鄂东网-首页\":3,\"xfwpx-校报\":4,\"中新网-首页\":6,\"xfwpx-要闻\":3,\"xfwpx-媒体\":3,\"微创资讯网-首页\":3},\"日本\":{\"体育信息聚合-科技\":1,\"体育信息聚合-互联网\":1,\"中国IDC服务网-新闻\":1,\"凯迪社区-时局深度\":1}}},{\"date\":\"2016-10-29\",\"results\":{\"中国\":{\"xfwpx-瞭望\":2,\"xfwpx-论谈\":3,\"xfwpx-校报\":2,\"wpchx-综合\":2,\"xfwpx-掠影\":2,\"xfwpx-媒体\":4,\"民生360-首页\":3,\"xfwpx-时评\":3,\"华律网-法律知识\":3}}},{\"date\":\"2016-10-30\",\"results\":{\"美国\":{\"安卓中国\":1},\"中国\":{\"xfwpx-综合\":2,\"湖北日报爆料台\":9,\"xfwpx-专题\":2,\"xfwpx-校报\":3,\"中新网-首页\":4,\"xfwpx-掠影\":3,\"xfwpx-媒体\":2,\"xfwpx-时评\":3,\"xfwpx-人物\":2}}},{\"date\":\"2016-10-31\",\"results\":{\"美国\":{\"ptxw.com\":2},\"中国\":{\"Gmw\":10,\"【快报】-留学\":4,\"人民网-首页\":22,\"Xinhuanet\":5,\"iPhone 6触摸屏-职场\":5,\"渤海新区官方网站-首页\":7,\"湖北日报爆料台\":13,\"eamii-首页\":5,\"iuwwm-首页\":5,\"jvhxp-首页\":4},\"日本\":{\"体育信息聚合-科技\":1,\"体育信息聚合-教育\":1,\"体育信息聚合-汽车\":1}}},{\"date\":\"2016-11-01\",\"results\":{\"中国\":{\"【快报】-留学\":8,\"人民网-首页\":55,\"中国行业信息化-高考\":9,\"【快报】-首页\":7,\"bxbbr-首页\":13,\"人民网教育-首页\":8,\"iPhone 6触摸屏-首页\":8,\"wgwmi-首页\":7,\"iuwwm-首页\":10,\"jvhxp-首页\":6}}},{\"date\":\"2016-11-02\",\"results\":{\"中国\":{\"人民网-首页\":61,\"颠覆规律的天价片酬-首页\":9,\"人民网教育-首页\":16,\"iPhone 6触摸屏-公考\":9,\"wgwmi-首页\":7,\"直播丰富大学生活-职场\":6,\"热点新闻网-IT数码\":6,\"数字化时代-首页\":10,\"iuwwm-首页\":6,\"人民网-金融\":6}}},{\"date\":\"2016-11-03\",\"results\":{\"美国\":{\"1979.com\":1},\"亚洲\":{\"河池视窗-新闻频道\":1},\"中国\":{\"热点新闻网-社会\":11,\"人民网-首页\":59,\"热点新闻网-首页\":23,\"颠覆规律的天价片酬-首页\":10,\"中国跳水梦之队-留学\":9,\"【快报】-首页\":13,\"人民网教育-首页\":29,\"iPhone 6触摸屏-首页\":15,\"wgwmi-首页\":9,\"iuwwm-首页\":9},\"日本\":{\"Vita之家-首页\":1,\"体育信息聚合-科技\":1,\"体育信息聚合-游戏\":1,\"体育信息聚合-体育\":1}}},{\"date\":\"2016-11-04\",\"results\":{\"中国\":{\"直播丰富大学生活-大学\":8,\"人民网-首页\":79,\"颠覆规律的天价片酬-首页\":8,\"iumyo-首页\":7,\"【快报】-首页\":12,\"人民网教育-首页\":14,\"中国跳水梦之队-首页\":13,\"iPhone 6触摸屏-首页\":10,\"wgwmi-首页\":12,\"数字化时代-首页\":8},\"日本\":{\"体育信息聚合-科技\":1}}},{\"date\":\"2016-11-05\",\"results\":{\"中国\":{\"人民网-首页\":35,\"人民网-教育\":5,\"【快报】-首页\":4,\"直播丰富大学生活-留学\":5,\"eamii-首页\":4,\"人民网教育-首页\":13,\"中国跳水梦之队-首页\":7,\"wpchx-综合\":4,\"jvhxp-首页\":4,\"360社区-360动态\":5}}},{\"date\":\"2016-11-06\",\"results\":{\"中国\":{\"东北新闻网-首页\":1,\"浙江网-国内新闻\":1,\"湖南网-国内新闻\":1,\"正义网-新闻\":1,\"wpchx-综合\":1,\"微热文-首页\":1,\"厦门网-首页\":1,\"微创资讯网-首页\":1,\"汉网-新闻\":1,\"传媒经济-首页\":1}}}]";
//		List<TGeisAnalyseNCMediaTrend> list = (List<TGeisAnalyseNCMediaTrend>)jsonToObject(test,ArrayList.class, TGeisAnalyseNCMediaTrend.class);
//		System.out.println(list.size());
//	}	
}
