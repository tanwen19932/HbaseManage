package handler.news;

import edu.buaa.nlp.entity.news.ProcessedNews;
import edu.buaa.nlp.http.PostConnection;
import edu.buaa.nlp.http.TranslateResultUtil;
import edu.buaa.nlp.tw.common.StringUtil;
import edu.buaa.nlp.util.DupConstants;
import edu.buaa.nlp.util.ExceptionUtil;
import edu.buaa.nlp.util.HtmlUtil;
import handler.Handler;

import java.util.concurrent.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TranslateHandlerTW
		implements Handler<ProcessedNews> {

	// private static String
	// paramSuffix="&detoken=true&nBestSize=5&align=true";//text=中国&srcl=zh&tgtl=en&detoken=true&nBestSize=5&align=true
	private static String paramSuffix = "";// "&detoken=true";//text=中国&srcl=zh&tgtl=en&detoken=true&nBestSize=5&align=true
	private static Logger LOG = LoggerFactory.getLogger( TranslateHandlerTW.class );
	private static ExecutorService pool = Executors.newFixedThreadPool( 100 );

	/**
	 * 翻译处理 1.中文翻译成英文 2.英文翻译成中文 3.其他语种，翻译中文和英文
	 * 
	 * @return -1:所有翻译项未全部通过,1：所有翻译项全通过
	 */

	@Override
	public boolean handle(ProcessedNews news) {
		if (news == null)
			return false;
		Future<Boolean> result = pool.submit( new Callable<Boolean>() {

			@Override
			public Boolean call()
					throws Exception {
				String param = "";
				try {
					if ( news.getLanguageCode().startsWith("zh") ) {
						news.setZhTitle ( news.getTitleSrc());
						news.setZhTxt( news.getTextSrc()  );
						news.setEnTitle( "" );
						// cwh add it 20160216 如果是中文，则不需要翻译
					} else if ("en".equalsIgnoreCase( news.getLanguageCode() )) {
						try {
							if( StringUtil.isNull(news.getZhTitle()) ){
								param = "&srcl=" + news.getLanguageCode() + "&tgtl=zh" + paramSuffix;
								LOG.debug(" 新闻 {}  新闻时间：{} zhTitle为空！" , news.getId() ,news.getPubdate() );
								String zhTitle = PostConnection.PostConnectionForTimes( DupConstants.TRANSLATE_SERVER_URL,
										HtmlUtil.clearHTML(news.getTitleSrc()).replaceAll( "[^(a-zA-Z0-9\\u4e00-\\u9fa5\\s\\p{P})]", " " ),
										DupConstants.TRIED_TRANSLATE_TIME );
								if ((zhTitle == null) || (zhTitle.trim().isEmpty())) {
									news.setZhTitle( news.getTitleSrc() );
									return false;
								} else {
									news.setZhTitle( HtmlUtil.clearHTML( zhTitle ) );
								}
								news.setEnTitle( news.getTitleSrc() );
							}
							else{
								LOG.debug(" 新闻 {}  新闻时间：{} zhTitle不为空！ zhTitle{}" , news.getId() ,news.getPubdate() ,news.getZhTitle() );
							}
						} catch (Exception e2) {
							LOG.info(" 新闻 {}  新闻时间：{} ehTitle{} 翻译失败" , news.getId() ,news.getPubdate() ,news.getEnTitle() );
							LOG.error("",e2);
							return false;
						}
						news.setEnTitle( news.getTitleSrc() );
						news.setEnTxt( news.getTextSrc() );
					} else {
						// 翻译成英文
						param = "&srcl=" + news.getLanguageCode() + "&tgtl=en" + paramSuffix;
						PostConnection post = new PostConnection( DupConstants.TRANSLATE_SERVER_URL );
						String enTitle = post.postResult( "text=" + news.getTitleSrc() + param );
						news.setEnTitle( TranslateResultUtil.getTranslateResult( enTitle ) );
						// String
						// enTxt=ConnectionUtil.sendPost(Constants.TRANSLATE_SERVER_URL,
						// "text="+news.getTxt()+param);

						/**
						 * 正文留待实时翻译，但是这样其他语言无法分类？用摘要分类？
						 */
						/*
						 * post=new
						 * PostConnection(Constants.TRANSLATE_SERVER_URL);
						 * String
						 * enTxt=post.postResult("text="+news.getTxtSrc()+param)
						 * ;
						 * news.setEnTxt(TranslateResultUtil.getTranslateResult(
						 * enTxt));
						 */
						// 翻译成中文
						param = "&srcl=" + news.getLanguageCode() + "&tgtl=zh" + paramSuffix;
						// String
						// zhTitle=ConnectionUtil.sendPost(Constants.TRANSLATE_SERVER_URL,
						// "text="+news.getTitle()+param);
						String zhTitle = new PostConnection( DupConstants.TRANSLATE_SERVER_URL )
								.postResult( "text=" + news.getTitleSrc() + param );
						news.setZhTitle( TranslateResultUtil.getTranslateResult( zhTitle ) );
						/**
						 * 正文留待实时翻译，但是这样其他语言无法分类？用摘要分类？
						 */
						/*
						 * String zhTxt=new
						 * PostConnection(Constants.TRANSLATE_SERVER_URL).
						 * postResult("text="+news.getTxtSrc()+param);
						 * news.setZhTxt(TranslateResultUtil.getTranslateResult(
						 * zhTxt));
						 */
					}

				} catch (Exception e) {
					LOG.error( ExceptionUtil.getExceptionTrace( e ) );
					// cwh add it 20160206 :
					// 若翻译失败，则直接丢弃，后续需要报警。但是目前摘要和关键词翻译不对，没有处理
					return false;
				}
				// logger.info(news+" at
				// translate["+Thread.currentThread().getName()+"]");
				return true;
			}
		} );
		try {
			return result.get();
		} catch (InterruptedException e) {
			LOG.error(ExceptionUtil.getExceptionTrace(e));
		} catch (ExecutionException e) {
			LOG.error( ExceptionUtil.getExceptionTrace( e ) );
		}
		return false;
	}
}
