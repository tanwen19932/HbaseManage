package controller;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import edu.buaa.nlp.entity.news.ProcessedNews;
import getter.AbstactGetter;
import getter.NewsGetter;
import handler.HandlerThread;
import handler.news.NewsHandlerChainV2;

public class NewsController {
	AbstactGetter<ProcessedNews> newsGetter;
	BlockingQueue<ProcessedNews> container = new LinkedBlockingQueue<>(Constants.CONTAINER_CORE_SIZE*2);

	public void start() {
		start(1);
	}
	public void start( int threadNum ) {
		newsGetter = new NewsGetter(container);
		newsGetter.start(1);
		HandlerThread<ProcessedNews> thread = new HandlerThread<>(container, NewsHandlerChainV2.class);
		thread.start(threadNum);
	}
}
