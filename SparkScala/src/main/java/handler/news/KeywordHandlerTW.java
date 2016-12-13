package handler.news;


import edu.buaa.nlp.entity.news.ProcessedNews;
import edu.buaa.nlp.text.inter.IKeyExtracter;
import edu.buaa.nlp.text.process.factory.TextProcessFactory;
import edu.buaa.nlp.text.process.keyword.KeyExtractor;
import handler.Handler;
import org.apache.log4j.Logger;

public class KeywordHandlerTW
		implements Handler<ProcessedNews> {

	private static Logger LOG =Logger.getLogger(KeywordHandlerTW.class) ;
	private IKeyExtracter keyExtracter = new KeyExtractor();;

	public KeywordHandlerTW() {
		// TODO Auto-generated constructor stub
		TextProcessFactory.getKeyExtractor().KeyExtractForString("初始化 是 很 必要 的", "cn", 5);
	}
	
	/**
	 * 关键词抽取
	 * @param news
	 * @return -1：抽取失败,1抽取成功
	 */
	@Override
	public boolean handle(ProcessedNews news) {
		if(news==null) return false;
		String content="";
		String lang="";
		if("zh".equalsIgnoreCase(news.getLanguageCode())){
			content=news.getZhTxtSeg();
			lang="cn";
		}else{
			content=news.getEnTxt();
			lang="en";
		}
		String[] keywords=null;
		try{
			//			keywords=TextProcessFactory.getKeyExtractor().KeyExtractForString(content, lang, 5);
			keywords=keyExtracter.KeyExtractForString(content, lang, 5);
		}catch(Exception e){
			LOG.error(""+(e));
		}
		
		if(keywords==null || keywords.length<1){
			LOG.error("关键词抽取：未抽取到关键词[" + news.getId()+"]");
			return false;
		}
		StringBuffer sb=new StringBuffer();
		for (int i=0; i<keywords.length; i++) {
			//关键词过长
			if(keywords[i].length()>50) continue;
			if(i == 0)
			{
				sb.append(keywords[i]);
			}
			else
			{
				sb.append(" ; " + keywords[i]);
			}
		}
		
		if("zh".equalsIgnoreCase(news.getLanguageCode())){
			news.setZhKeywords(sb.toString());
			//cwh add it 20160216 如果是中文，则不需要翻译
			/*
			String param="&srcl="+news.getLanguageCode()+"&tgtl=en"+paramSuffix;
			String enKeyword = null;
			try {
				enKeyword=new PostConnection(Constants.TRANSLATE_SERVER_URL).postResult("text="+news.getZhKeywords()+param);
				news.setEnKeywords(TranslateResultUtil.getTranslateResult(enKeyword));
			} catch (Exception e) {
				logger.error(ExceptionUtil.getExceptionTrace(e));
			}
			*/
		}else{
			news.setEnKeywords(sb.toString());
			//如果是英文，翻译成中文关键词

/*			String zhKeyword = PostConnection.PostConnectionForTimes(Constants.TRANSLATE_SERVER_URL,news.getEnKeywords(),news.getLanguageCode(),"zh", Constants.TRIED_TRANSLATE_TIME );
			if((zhKeyword ==null) || (zhKeyword.trim().isEmpty()))
			{
				news.setZhKeywords(news.getEnKeywords());
			}
			else
			{
				news.setZhKeywords(HtmlUtil.clearHTML(zhKeyword).replaceAll("；", ";"));
			}*/


		}
		LOG.debug(" 新闻  " + news.getId() + "关键词 中文：" + news.getZhKeywords()+ "英文" + news.getEnKeywords());
		return true;
	}
}
