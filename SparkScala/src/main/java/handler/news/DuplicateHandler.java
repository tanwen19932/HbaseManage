package handler.news;

import edu.buaa.nlp.duplicate.HbaseNewsDupDetect;
import edu.buaa.nlp.duplicate.IDupDetector;
import edu.buaa.nlp.entity.news.ProcessedNews;
import handler.QueueHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executors;

public class DuplicateHandler extends QueueHandler<ProcessedNews>{
	private static final Logger LOG = LoggerFactory.getLogger(DuplicateHandler.class);
	
	private static IDupDetector<ProcessedNews> duplicateDetector = HbaseNewsDupDetect.getInstance();
	
	@Override
	public void run() {
		threadPool = Executors.newFixedThreadPool(threadNum);
		for (int i = 0; i < threadNum; i++) {
			threadPool.submit(new Runnable() {
				List<ProcessedNews> getHandleList(){
					List<ProcessedNews> newsList = new LinkedList<>();
					int retry = 0;
					while(true){
						ProcessedNews obj = queue.poll();
						if(obj==null){
							retry++;
							try {
								Thread.sleep(2000);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
						else{
							newsList.add(obj);
						}
						if( retry==5 || newsList.size()>500 ){
							break;
						}
					}
					return newsList;
				}
				@Override
				public void run() {
					while (true) {
						try {
							List<ProcessedNews> newsList = getHandleList();
//							boolean[] result = duplicateDetector.isDup(newsList);
//							for(int i ; i< result.length ; i++){
//								if(result[i]){
//									next.add(newsList.get(i));
//								}
//								else errorHandler.errorHandle("Duplicate", newsList.get(i));
//							}
						} catch (Exception e) {
							LOG.error(this.getClass().getName() + "处理线程 ： ", e);
						}
					}
				}
			});
		}
	}
}
