package handler.news;

import edu.buaa.nlp.entity.news.DicMap;
import edu.buaa.nlp.entity.news.News;
import edu.buaa.nlp.entity.news.ProcessedNews;
import edu.buaa.nlp.tw.common.DateUtil;
import edu.buaa.nlp.tw.common.FileUtils;
import edu.buaa.nlp.tw.common.HtmlUtil;
import handler.Handler;
import handler.HandlerChain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mininglamp.nlp.languageDetect.LanguageUtil;
import com.mininglamp.nlp.languageDetect.impl.LangRecog;

import statistics.CountModel;
import statistics.Counter;
import statistics.ERROR;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static edu.buaa.nlp.tw.common.StringUtil.*;

class NewsMediaNameHandler implements Handler<ProcessedNews> {
	@Override
	public boolean handle(ProcessedNews news) {

		if (isNull(news.getMediaNameZh())) {
			if (!isNull(news.getMediaNameSrc())) {
				news.setMediaNameZh(news.getMediaNameSrc());
			} else if (!isNull(news.getMediaNameEn())) {
				news.setMediaNameZh(news.getMediaNameEn());
				news.setMediaNameSrc(news.getMediaNameEn());
			} else {
				String domain = HtmlUtil.getDomain(news.getUrl());
				news.setMediaNameEn(domain);
				news.setMediaNameZh(domain);
				news.setMediaNameSrc(domain);
			}
		}
		if (isNull(news.getMediaNameSrc())) {
			news.setMediaNameSrc(news.getMediaNameZh());
		}
		if (news.getMediaNameZh().equalsIgnoreCase("国家食品药品监督管理局")) {
			return false;
		}
		return !isAllNull(news.getMediaNameZh(), news.getMediaNameEn(), news.getMediaNameSrc());
	}
}

class NewsTypeAndCountryHandler implements Handler<ProcessedNews> {
	@Override
	public boolean handle(ProcessedNews news) {
		if (!isNull(news.getMediaTname())) {
			news.setMediaType(DicMap.getMediaType(news.getMediaTname()));
		} else {
			news.setMediaTname(DicMap.getMediaTName(news.getMediaType()));
		}
		// 国家
		if (isNull(news.getCountryNameEn()) && !isNull(news.getCountryNameZh())) {
			news.setCountryNameEn(DicMap.getCountryEn(news.getCountryNameZh()));
		} else if (!isNull(news.getCountryNameEn()) && isNull(news.getCountryNameZh())) {
			news.setCountryNameZh(DicMap.getCountryZh(news.getCountryNameEn()));
		}
		news.setCountryNameZh(DicMap.getCountryZh(news.getCountryNameEn()));
		return !isOneNull(news.getMediaTname(), news.getCountryNameEn(), news.getCountryNameZh());
	}
 
}

class NewsContentHandler implements Handler<ProcessedNews> {
	
	@Override
	public boolean handle(ProcessedNews news) {
		news.setTitleSrc(HtmlUtil.rmTagsByJsoup(news.getTitleSrc()));
		news.setTextSrc(HtmlUtil.rmTagsByJsoupWithBr(news.getTextSrc()));
		news.setTitleSrc(edu.buaa.nlp.util.HtmlUtil.clearTitleHTML((news.getTitleSrc())));
		news.setTextSrc(edu.buaa.nlp.util.HtmlUtil.clearHTML(news.getTextSrc()));
		news.setDocLength(news.getTitleSrc().length() + news.getTextSrc().length());
		if (news.getTextSrc().replaceAll("\\s", "").length() < 30) {
			return false;
		}
		return !isOneNull(news.getTitleSrc(), news.getTextSrc());
	}
}

class NewsTimeHandler implements Handler<ProcessedNews> {
	@Override
	public boolean handle(ProcessedNews news) {
		news.setPubdate(DateUtil.tryParse(news.getPubdate()));
		Date pubDate = DateUtil.getDate(news.getPubdate());
		if ((pubDate.getTime() > System.currentTimeMillis())) {
			Date fixDate = DateUtil.getFixDate(news.getPubdate());
			if (fixDate.getTime() > System.currentTimeMillis()) {
				return false;
			} else
				news.setPubdate(DateUtil.getFixDateStr(fixDate));
		}
		if (isNull(news.getCreated())) {
			news.setCreated(news.getPubdate());
		}
		news.setUpdated(DateUtil.getFixDateStr(System.currentTimeMillis()));
		// if()
		return !isNull(news.getPubdate());
	}
}

class NewsLanguageHandler implements Handler<ProcessedNews> {
	static Logger LOG = LoggerFactory.getLogger(NewsLanguageHandler.class);
	static List<String> allowLanguage = new ArrayList<>();
	static {
		allowLanguage.add("en");
		allowLanguage.add("zh");
		allowLanguage.add("zh-tw");
	}

	boolean checkLan(ProcessedNews news) {
		for (String lan : allowLanguage) {
			if (news.getLanguageCode().equals(lan)) {
				return true;
			}
		}
		news.setLanguageCode("other");
		return false;
	}

	@Override
	public boolean handle(ProcessedNews aNews) {
		// 语言识别
		String detectLang = LanguageUtil.getInstance().detect(aNews.getTitleSrc() + "\n" + aNews.getTextSrc().replaceAll("<BR/>", ""));
		// System.out.println( "检测语言:"+ detectLang + aNews.getTitleSrc() +
		// aNews.getTextSrc() + " ------>>");
		LOG.debug("新闻ID: {} 检测语言: {} 标题内容：{}",aNews.getId(),detectLang , aNews.getTitleSrc());
		if (!isNull(detectLang) && !(detectLang.equalsIgnoreCase(aNews.getLanguageCode()))) {
			aNews.setLanguageCode(detectLang);
			aNews.setLanguageTname(DicMap.getLanguageZh(aNews.getLanguageCode()));
		}
		if (isAllNull(aNews.getLanguageCode(), aNews.getLanguageTname())) {
			if (isNull(detectLang))
				detectLang = "other";
			aNews.setLanguageCode(detectLang);
			aNews.setLanguageTname(DicMap.getLanguageZh(aNews.getLanguageCode()));
		} else if (!isNull(aNews.getLanguageCode()) && isNull(aNews.getLanguageTname())) {
			aNews.setLanguageTname(DicMap.getLanguageZh(aNews.getLanguageCode()));
		} else if (isNull(aNews.getLanguageCode()) && !isNull(aNews.getLanguageTname())) {
			aNews.setLanguageCode(DicMap.getLanguageEn(aNews.getLanguageTname()));
		}
		return (!isOneNull(aNews.getLanguageCode(), aNews.getLanguageTname())) && checkLan(aNews);
	}
}
