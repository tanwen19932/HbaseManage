package getter;

import controller.Constants;
import edu.buaa.nlp.entity.news.ProcessedNews;
import edu.buaa.nlp.ws.client.dataProvider.DataServiceImplService;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;

import java.net.URL;
import java.util.concurrent.BlockingQueue;

public class NewsGetter extends AbstactGetter<ProcessedNews> {

	private static Logger logger = Logger.getLogger(NewsGetter.class);

	public NewsGetter(BlockingQueue<ProcessedNews> container) {
		super(container);
	}

	@Override
	public String getStrByService() {
		// 从WebService中读取
		// ConnectException
		String str = "";
		long s, t;
		s = System.currentTimeMillis();
		try {
			DataServiceImplService service = new DataServiceImplService(new URL(Constants.DATA_CONTROLLER_URL));
			str = service.getDataServiceImplPort().fetchData(Constants.BEGIN_DATE_TIME, Constants.END_DATE_TIME, Constants.PRODUCE_DATA_LANGUAGE);
			t = System.currentTimeMillis();
			logger.info("fetching data completed : " + (t - s) + "ms");
		} catch (Exception e) {
			logger.error("数据读取错误 等待10s：");
			logger.error("数据读取：" ,e);
			try {
				Thread.sleep(10* 1000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}catch (Error e) {
			logger.error("致命错误 将会退出程序",e);
		}
		return str;
	}

	@Override
	ProcessedNews getAEntity(String json) {
		ProcessedNews news = (ProcessedNews) JSONObject.toBean(JSONObject.fromObject(json), ProcessedNews.class);
		return news;
	}
}
