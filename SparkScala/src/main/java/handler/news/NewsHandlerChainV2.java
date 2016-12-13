package handler.news;

import edu.buaa.nlp.entity.news.ProcessedNews;
import edu.buaa.nlp.entity.util.HbaseUtil;
import edu.buaa.nlp.tw.common.FileUtils;
import handler.Handler;
import handler.HandlerChain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;


public class NewsHandlerChainV2 extends HandlerChain<ProcessedNews> {
	private static final Logger LOG = LoggerFactory.getLogger(NewsHandlerChainV2.class);

	public void errorHandle(String errorName, ProcessedNews news) {
		FileUtils.fileAppendStr( errorName, news.getId() );
	}

	@Override
	protected String getErrorMessage(Handler<ProcessedNews> handler) {
		return handler.getClass().getSimpleName().replaceAll( "Handler(TW)?", "" );
	}

	@Override
	protected void init() {
		errorHandler = new MysqlErrorHandler();
		handlers = new LinkedList<Handler<ProcessedNews>>();
		handlers.add(new NewsMediaNameHandler());
		handlers.add(new NewsTypeAndCountryHandler());
		handlers.add(new NewsTimeHandler());
		handlers.add(new NewsContentHandler());
		handlers.add(new NewsLanguageHandler());
		handlers.add(new NewsSensitiveHandler());
		
		handlers.add(new DuplicateHandlerTW());
		handlers.add(new TranslateHandlerTW());
		
		handlers.add(new SegmentHandlerTW());
		handlers.add(new ClassifyHandlerTW());
		handlers.add(new KeywordHandlerTW());
		
		SentimentHandlerTW.quick=false;
		handlers.add(new SentimentHandlerTW());
		handlers.add(new SummaryHandlerTW());
		
		HbaseUtil.configuration = HbaseUtil.configuration50;
		handlers.add(new SaveHandlerTW());
	}
}
