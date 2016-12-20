package handler.news;

import edu.buaa.nlp.entity.news.ProcessedNews;
import handler.ErrorHandler;

import java.io.Serializable;

public class EmptyErrorHandler implements ErrorHandler<ProcessedNews> ,Serializable{
	static final String SUCCESS_MSG = "Success";
	@Override
	public void errorHandle(String errorMessage, ProcessedNews news) {
		System.out.println(errorMessage );
	}

	@Override
	public void successHandle(ProcessedNews news) {
	}

}
