package handler.news;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.buaa.nlp.entity.news.ProcessedNews;
import edu.buaa.nlp.text.inter.ISummerizer;
import edu.buaa.nlp.text.process.summ.LexRankSummarizer;
import edu.buaa.nlp.util.CharUtil;
import edu.buaa.nlp.util.ExceptionUtil;
import edu.buaa.nlp.util.HtmlUtil;
import handler.Handler;

import java.io.Serializable;

public class SummaryHandlerTW
		implements Handler<ProcessedNews>,Serializable {

	private static final Logger LOG = LoggerFactory.getLogger(SummaryHandlerTW.class) ;
	private  ISummerizer summerizer = new LexRankSummarizer();
	private  String paramSuffix="";//"&detoken=true&nBestSize=5&align=true";//text=中国&srcl=zh&tgtl=en&detoken=true&nBestSize=5&align=true
	
	public SummaryHandlerTW() {
	}
	/**
	 * 摘要抽取
	 * @param news
	 * @return -1：抽取失败,1抽取成功
	 */
	@Override
	public boolean handle(ProcessedNews news) {
		if(news==null) return false;
		int len=0;
		String content="";
		String lang="";
		if("zh".equalsIgnoreCase(news.getLanguageCode())){
			content=news.getZhTxtSeg();
			lang="cn";
			len=300;
		}else{
			lang="en";
			content=news.getEnTxt().replaceAll("<BR/>", "\r\n");
			len=500;
		}
		String summary="";
		try{
			//			summary=TextProcessFactory.getSummerizor().SummarizeForString(content, lang, len, true);
			//System.out.println(content);
			if(content.length() > len)
			{
				synchronized (summerizer) {
					summary=summerizer.SummarizeForString(content, lang, len, true).replaceAll("\\.\\.\\.", " ");
				}
			}
			else
			{
				summary = content;
			}
		}catch(Exception e){
			LOG.error(ExceptionUtil.getExceptionTrace(e));
		}
		if(summary==null || "".equals(summary.trim())){
			summary = content; //return -1;
		}
		//长度检查
		if(summary.length()>len) summary=summary.substring(0, len);
		if("zh".equalsIgnoreCase(news.getLanguageCode())){
			summary=HtmlUtil.delBlank(summary);
			summary=CharUtil.removeSpaceInChinese(summary);
			news.setZhSummary(summary);
			//cwh add it 20160216 如果是中文，则不需要翻译。摘要还是翻译
			/*
			String param="&srcl="+news.getLanguageCode()+"&tgtl=en"+paramSuffix;
			try {
//				enSummary = ConnectionUtil.sendPost(Constants.TRANSLATE_SERVER_URL, "text="+news.getZhSummary()+param);
				try
				{
					List<String> sentences=SplitSentence.splitSentence(news.getZhSummary(), "zh");
					StringBuffer enSummary=new StringBuffer();
					for(int i=0; i<sentences.size(); i++){
						if(sentences.get(i).equalsIgnoreCase("<BR/>"))
						{
							enSummary.append("<BR/>");
						}
						else
						{
							String r=new PostConnection(Constants.TRANSLATE_SERVER_URL).postResult("text="+sentences.get(i)+param);
							enSummary.append(TranslateResultUtil.getTranslateResult(r));
						}
					}
					news.setEnSummary(enSummary.toString());
				}
				catch(Exception e2)
				{
					e2.printStackTrace();
				}

			} catch (Exception e) {
				logger.error(ExceptionUtil.getExceptionTrace(e));
			}
			//*/
		}else{
			summary=CharUtil.removeSpaceInEnglish(summary).replaceAll("[\r\n]", " ");
			news.setEnSummary(summary);
			String param="&srcl=en"+"&tgtl=zh"+paramSuffix;
			//*
			try
			{
				//String zhSummary = ConnectionUtil.sendPost(Constants.TRANSLATE_SERVER_URL, "text="+news.getEnSummary()+param);
				//					String zhSummary=new PostConnection(Constants.TRANSLATE_SERVER_URL).postResult("text="+news.getEnSummary()+param);
				//					news.setZhSummary(zhSummary.toString());

				/**
				 * 不翻译摘要
				 */
				news.setZhSummary(news.getEnSummary());
				/*
				List<String> sentences=SplitSentence.splitSentence(news.getEnSummary(), "en");
				StringBuffer zhSummary=new StringBuffer();
				for(int i=0; i<sentences.size(); i++){
					if(sentences.get(i).equalsIgnoreCase("<BR/>")) continue;
					String r = PostConnection.PostConnectionForTimes(Constants.TRANSLATE_SERVER_URL, sentences.get(i),Constants.TRIED_TRANSLATE_TIME );
					if((r ==null) || (r.trim().isEmpty()))
					{
						continue;
					}
					else
					{
						zhSummary.append(HtmlUtil.clearHTML(r) + " ");
					}
				}
				if(zhSummary.toString().trim().isEmpty())
				{
					news.setZhSummary(news.getEnSummary());
				}
				else
				{
					news.setZhSummary(zhSummary.toString().trim());
				}
				*/


			}
			catch(Exception e)
			{

				LOG.error(ExceptionUtil.getExceptionTrace(e));
				return false;
			}

		}
		LOG.debug(" 新闻 {} \t 摘要 ：{} \t" , news.getId() ,news.getZhSummary() );
		return true;
	}
}
