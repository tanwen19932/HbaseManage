package handler.news;

import edu.buaa.nlp.entity.news.ProcessedNews;
import edu.buaa.nlp.tw.common.HtmlUtil;
import edu.buaa.nlp.util.ExceptionUtil;
import handler.Handler;
import org.ansj.domain.Result;
import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.ToAnalysis;
import org.apache.log4j.Logger;

import java.io.Serializable;

public class SegmentHandlerTW implements Handler<ProcessedNews> ,Serializable {
	private static Logger logger = Logger.getLogger(SegmentHandlerTW.class);

	@Override
	public boolean handle(ProcessedNews news) {

		if ("zh".equalsIgnoreCase(news.getLanguageCode())) {
			{
				String title = HtmlUtil.rmTagsByJsoup(news.getTitleSrc());
				String content = HtmlUtil.rmTagsByJsoup(news.getTextSrc());
				try {
//						title = segmentor.seg(title);
//						content = segmentor.seg(content.replaceAll("<BR/>", "\r\n\r\n")); // <BR/>标签处理的怎么
					title = parse(title);
					content = parse(content.replaceAll("<BR/>", "\r\n\r\n"));

				} catch (Exception e) {
					logger.error("ID:" + news.getId() + ExceptionUtil.getExceptionTrace(e));
					return false;
				} catch (Error error) {
					logger.error("分词：" + ExceptionUtil.getExceptionTrace(error));
					return false;
				}
				news.setZhTitle(news.getTitleSrc());
				// 分词后的结果，去除html标签
				news.setZhTitleSeg(title);
				news.setZhTxtSeg(content);
				// logger.info(news+" at
				// segment["+Thread.currentThread().getName()+"]");
				return true;
			}
		}
		return true;
	}

	private String parse(String text) {
		StringBuilder textBuilder = new StringBuilder();
		Result result = ToAnalysis.parse(text);
			for(Term term : result) {
			textBuilder.append(term.getName() + " ");
		}
		return textBuilder.toString();
	}
	
	public static void main(String[] args) {
		String text = "引子 ：都说窜货是产品动销乃至产品生存的癌症 ，顽疾 ，每个企业都把管控窜货放在至关重要的位置 ，有的企业甚至规定一次窜货罚款高达十几万 。 但结果呢 ？无论哪个厂家治理力度多么大 ，窜货依然围绕在每个企业产品身边 ，从未消失 。 <BR/>一 、顽疾的由来 <BR/>我们说窜货也不能以偏概全 ，要区别来看 。";
		String content = HtmlUtil.rmTagsByJsoup(text);
//		content = parse(content.replaceAll("<BR/>", "\r\n\r\n"));
		System.out.println(content);
	}
	
}