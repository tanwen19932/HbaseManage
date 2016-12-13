package handler.news;

import edu.buaa.nlp.entity.news.ProcessedNews;
import edu.buaa.nlp.tw.common.DateUtil;
import handler.ErrorHandler;
import statistics.CountModel;
import statistics.Counter;
import statistics.ERROR;

public class MysqlErrorHandler implements ErrorHandler<ProcessedNews> {
	static final String SUCCESS_MSG = "Success";

	@Override
	public void errorHandle(String errorMessage, ProcessedNews news) {
		Counter.increase(new CountModel(news.getComeFrom(), ERROR.valueOf(errorMessage), news.getLanguageCode(),
				DateUtil.getyyyy_MM_dd(System.currentTimeMillis())));
	}

	@Override
	public void successHandle(ProcessedNews news) {
		Counter.increase(new CountModel(news.getComeFrom(), ERROR.valueOf(SUCCESS_MSG), news.getLanguageCode(),
				DateUtil.getyyyy_MM_dd(System.currentTimeMillis())));
	}
}
