package news.goonieNews.hbase;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import news.News;
import news.mySqlNewsToHbase.MysqlNewsInserter;
import tw.utils.HtmlUtil;




public class GoonieMysql extends MysqlNewsInserter{

	@Override
	public String generateSql(int page) {
		StringBuffer sb = new StringBuffer();
		//SELECT *, gooniearticle.typeid FROM gooniearticle 
		//LEFT JOIN gooniearticledetailed ON gooniearticle.pid = gooniearticledetailed.id 
		//LEFT JOIN gooniearea ON gooniearticle.site_code = gooniearea.id
		//LEFT JOIN goonieurltype ON gooniearticle.typeid = goonieurltype.id
		//limit 10 offset 0;
		sb.append("SELECT *, gooniearticle.typeid, gooniearea.description, gooniearticle.description FROM gooniearticle"
				+ " LEFT JOIN gooniearticledetailed ON gooniearticle.id = gooniearticledetailed.pid" 
				+ " LEFT JOIN gooniearea ON gooniearticle.site_code = gooniearea.id"
				+ " LEFT JOIN goonieurltype ON gooniearticle.typeid = goonieurltype.id"
				+ " LEFT JOIN gooniesitesarea ON gooniesitesarea.id = gooniearticle.site_area_code"
				+ " a where a.id>"+(pageSize*page+startRow)  + "  limit " 
				+ pageSize );
		return  sb.toString();
	}

	@Override
	public News getNewsFromRS(ResultSet rs) throws SQLException {
		String id = null; //byte[] id ;
		
		int mediaType = 1 ;//媒体类型id
		String mediaTname = "新闻";// 新闻
		
		String titleSrc = rs.getString(2);
		String pubdate = rs.getString(3).substring(0, 19); 	//发布时间
		String textSrc = rs.getString(4);
		
		String websiteId = rs.getString(5);	//站点id
		
		String mediaNameSrc = null;//数据源名称
		String mediaNameZh = rs.getString(6);
		String mediaNameEn = null;;
		int mediaLevel = 4 ; //级别
		String countryNameZh = null;
		String countryNameEn = null;
		String provinceNameZh = null;
		String provinceNameEn = null;
		String districtNameZh = null;
		String districtNameEn = null;
		String languageCode =null;//en zh
		String languageTname = null;
		
		String url = rs.getString(9);
		Map<String, String > valueMap = mediaSrcMap.get( HtmlUtil.getDomain(url) );
		if(valueMap == null){
			System.err.println("数据源不在 数据源库");
			countryNameZh = "其他";
			countryNameEn = "other";
			
		}
		else{
			mediaNameZh = valueMap.get("mediaNameZh");
			mediaNameSrc = valueMap.get("mediaNameSrc");
			mediaNameEn  = valueMap.get("mediaNameEn");
			String levelString =  valueMap.get("mediaLevel") ;
			if( levelString != null)
				mediaLevel = Integer.valueOf( levelString ); //级别
			countryNameZh = valueMap.get("countryNameZh");
			countryNameEn = valueMap.get("countryNameEn");
			provinceNameZh = valueMap.get("provinceNameZh");
			provinceNameEn = valueMap.get("provinceNameEn");
			districtNameZh = valueMap.get("districtNameZh");
			districtNameEn = valueMap.get("districtNameEn");
		}
	
		languageCode =rs.getString(7);//en zh
		languageTname = "中文";
		
		String author = null;
		
		String created = rs.getString(8).substring(0, 19) ; 	//爬取时间
		String updated = rs.getString(8).substring(0, 19) ;	//更新时间
		
		boolean isOriginal = true;//是否原创
		
		int view = 0;
		
		int docLength =  titleSrc.length()+textSrc.length() ;
		
		String transFromM = null;
		int pv = 0;
		boolean isHome = true; //是否首页
		boolean isPicture = false;
		
		String comeFrom = "Goonie";
		
		News news = new News(id, mediaType, mediaTname, titleSrc, pubdate, textSrc, websiteId, mediaNameSrc, mediaNameZh, mediaNameEn, mediaLevel, countryNameZh, countryNameEn, provinceNameZh, provinceNameEn, districtNameZh, districtNameEn, languageCode, languageTname, author, created, updated, isOriginal, view, url, docLength, transFromM, pv, isHome, isPicture, comeFrom);
		
		News gn = new News();
		gn.setAuthor(rs.getString("creator"));
		gn.setComeFrom("Goonie");
		gn.setComeFromDb("zyyt");
		gn.setCountryNameEn(rs.getString("gooniearea.description"));
		gn.setCountryNameZh("");
		gn.setCreated(rs.getString("gather_time"));
		gn.setDistrictNameEn(rs.getString("gooniearticle.description"));
		gn.setDistrictNameZh("");
		gn.setDocLength(rs.getInt("txt_length"));
		gn.setHome(true);
		gn.setOriginal(true);
		gn.setPicture(false);
		gn.setLanguageCode(rs.getString("language"));
		gn.setLanguageTname("");
		gn.setMediaLevel(1);
		gn.setMediaNameEn(rs.getString("source"));
		gn.setMediaTname(rs.getString("typename"));
		gn.setMediaType(rs.getInt("gooniearticle.typeid"));
		gn.setProvinceNameEn(rs.getString("gooniearticle.description"));
		gn.setProvinceNameZh("");
		gn.setPubdate(rs.getString("publish_time"));
		gn.setPv(0);
		gn.setTextSrc(rs.getString("content"));
		gn.setTitleSrc(rs.getString("summary"));
		gn.setTransFromM("");
		gn.setUpdated(rs.getString("publish_time"));
		gn.setUrl(rs.getString("url"));
		gn.setUserTag("");
		gn.setView(0);
		gn.setWebsiteId("");
		return gn;
	}
	public static void main(String[] args) {
		GoonieMysql goonieMysql = new GoonieMysql();
		goonieMysql.start(2);
	}
}
