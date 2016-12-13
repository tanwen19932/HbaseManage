package handler.news;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

import edu.buaa.nlp.entity.news.ProcessedNews;
import edu.buaa.nlp.tw.common.DateUtil;
import handler.ErrorHandler;
import handler.Handler;
import handler.HandlerChain;
import handler.QueueHandler;
import statistics.CountModel;
import statistics.Counter;
import statistics.ERROR;

public class NewsHandlerFlock extends QueueHandler<ProcessedNews>{
	public NewsHandlerFlock() {
		super();
		HandlerChain<ProcessedNews> newsHandlerChainPre = new HandlerChain<ProcessedNews>() {
			@Override
			protected String getErrorMessage(Handler<ProcessedNews> handler) {
				// TODO Auto-generated method stub
				return  handler.getClass().getSimpleName().replaceAll("Handler(TW)?", "");
			}

			@Override
			protected void init() {
				errorHandler = new MysqlErrorHandler(){
					@Override
					public void successHandle(ProcessedNews news) {
					}
				};
				handlers = new LinkedList<Handler<ProcessedNews>>();
				handlers.add(new NewsMediaNameHandler());
				handlers.add( new NewsTypeAndCountryHandler() );
				handlers.add(new NewsContentHandler());
				handlers.add(new NewsTimeHandler());
				handlers.add(new NewsLanguageHandler());
				handlers.add(new NewsSensitiveHandler());
			}
		};
		this.setHandler(newsHandlerChainPre);
		this.setNext(next);
	}
	

}
