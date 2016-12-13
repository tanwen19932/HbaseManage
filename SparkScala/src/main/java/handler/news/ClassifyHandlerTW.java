package handler.news;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.buaa.nlp.TextClassification.ThemeClassifier;
import edu.buaa.nlp.entity.news.ProcessedNews;
import handler.Handler;

/***
 * 比较耗CPU
 * 
 * @author Vincent
 * 
 */
public class ClassifyHandlerTW
		implements Handler<ProcessedNews> {

	private static Logger LOG = LoggerFactory.getLogger(ClassifyHandlerTW.class);

	@Override
	public boolean handle(ProcessedNews news) {
		if (news == null)
			return false;
		String content = "";
		String lang = "";
		if (news.getLanguageCode().startsWith("zh")) {
			content = news.getZhTxtSeg();
			lang = "zh";
		} else {
			content = news.getEnTxt().replaceAll("<BR/>", "\r\n");
			lang = "en";
		}
		ThemeClassifier themeClassifier = new ThemeClassifier();
		int result = themeClassifier.predict(content, lang);
		news.setCategoryId(result);
		LOG.debug(" 新闻ID: {}  {} 分类名称 ：{} 分类预测内容长度：{} 内容前100：{}",news.getId() ,news.getLanguageCode(), news.getCategoryName(), content.length(),content.substring(0, content.length()>100?100:content.length()));
		return true;
	}

}
