package main;

import controller.Constants;
import controller.NewsController;
import edu.buaa.nlp.exception.ThreadUncaughtExceptionHandler;
import edu.buaa.nlp.util.DateUtil;
import org.apache.log4j.Logger;

import java.util.Date;


/**
 * @author Vincent
 *
 */
public class MainNews {

	private static Logger logger=Logger.getLogger(MainNews.class);
	
	public static void main(String[] args) {
		NewsController controller = new NewsController();
		logger.info("  去重容器为——————————  " + Constants.BEGIN_DATE_TIME + "  " + Constants.END_DATE_TIME + "  "
				+ Constants.PRODUCE_DATA_LANGUAGE +"  "+ Constants.DATA_CONTROLLER_URL);

		controller.start(Constants.PREPROCESS_THREAD_NUM);
		//注册JVM钩子
		//Runtime.getRuntime().addShutdownHook(new Thread(new RestartTask()));
		//启动任务
		Thread.setDefaultUncaughtExceptionHandler(new ThreadUncaughtExceptionHandler());
		
		logger.info("任务开始："+DateUtil.getForDate("yyyy-MM-dd HH:mm:ss", new Date()));
  
	}
}
