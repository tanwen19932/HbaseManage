package handler.news;

import edu.buaa.nlp.entity.news.ProcessedNews;
import edu.buaa.nlp.sentiment.orientation.CombineSentimentAnalysis;
import edu.buaa.nlp.text.inter.ISentimentAnalysis;
import edu.buaa.nlp.util.DateUtil;
import edu.buaa.nlp.util.ExceptionUtil;
import handler.Handler;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SentimentHandlerTW implements Handler<ProcessedNews> {
	private static final Logger LOG = LoggerFactory.getLogger(SentimentHandlerTW.class);
	 public static boolean quick = false;
	private ISentimentAnalysis sentimentClassifier ;
	public SentimentHandlerTW() {
		sentimentClassifier = new CombineSentimentAnalysis();
		sentimentClassifier.analyseParagraph("中国", "zh");
	}
	@Override
	public boolean handle(ProcessedNews news) {
		if (news == null)
			return false;
		
		int s = 0;
		news.setSentimentId(s);
        news.setSentimentName("中性");
		LOG.debug(" 新闻" + news.getId() + "情感：" + news.getSentimentName());
		if(quick)
			return true;
		
		String content = "";
		String lang = "";
		if ("zh".equalsIgnoreCase(news.getLanguageCode())) {
			content = news.getZhTxtSeg();
			lang = "zh"; // news.getLanguageCode();
		} else {
			content = news.getEnTxt().replace("<BR/>", "\r\n");
			lang = "en";
		}
		/*
		 * try{ if(news.getLangId()==2){ content=news.getContentSrc();
		 * content=HtmlUtil.delHtmlTag(content);
		 * content=HtmlUtil.delEnter(content);
		 * enSentimentClassifier.analyseParagraph(content); }else
		 * if(news.getLangId()==1){
		 * s=SentimentClassifier.analyseParagraph(content); } }catch(Exception
		 * e){ e.printStackTrace(); System.out.println(news.getContentSrc()); }
		 */
		int retry =0;
		while (true) {
			try {
				// s=TextProcessFactory.getSentiAnalyser().analyseParagraph(content,
				// lang);
				if(retry == 3){
					s = 0;
					break;
				}
				s = sentimentClassifier.analyseParagraph(content, lang);
				break;
			}catch(NullPointerException e1){
				LOG.error("情感分析错误", e1);
				retry++;
				continue;
			}catch (Exception e) {
				LOG.error(ExceptionUtil.getExceptionTrace(e));
				try {
					String indexPath = "errlang." + DateUtil.getToday8() + ".txt";
					FileOutputStream fos = new FileOutputStream(indexPath, true);
					OutputStreamWriter osw = new OutputStreamWriter(fos, "utf-8");
					osw.write(content + "\r\n\r\n");
					osw.flush();
					osw.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
				s = 0;// 如果报错，结果设为0
				break;
			}
		}
		news.setSentimentId(s);
		if (s > 0) {
			news.setSentimentName("正面");
		} else if (s < 0) {
			news.setSentimentName("负面");
		} else {
			news.setSentimentName("中性");
		}
		LOG.debug(" 新闻: {} \t 情感 ：{} \t" , news.getId() ,news.getSentimentName() );
		return true;
	}
}
