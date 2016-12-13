package handler.news;

import edu.buaa.nlp.entity.news.ProcessedNews;
import edu.buaa.nlp.tw.common.DateUtil;
import handler.ErrorHandler;
import statistics.CountModel;
import statistics.Counter;
import statistics.ERROR;

public class EmptyErrorHandler implements ErrorHandler<ProcessedNews>{
	static final String SUCCESS_MSG = "Success";
	@Override
	public void errorHandle(String errorMessage, ProcessedNews news) {
		System.out.println(errorMessage );
	}

	@Override
	public void successHandle(ProcessedNews news) {
	}

}
