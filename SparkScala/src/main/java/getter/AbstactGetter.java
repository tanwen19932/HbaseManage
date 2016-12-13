package getter;

import controller.Constants;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public abstract class AbstactGetter<T> implements IGetter {
	private BlockingQueue container;
	private static Logger LOG = LoggerFactory.getLogger(NewsGetter.class);
	public static AtomicLong totalRawsCount = new AtomicLong();

	public AbstactGetter(BlockingQueue<T> container) {
		this.container = container;
	}

	@SuppressWarnings("null")
	public void insertToContainer() throws InterruptedException {
		while(container.size()>2000 ){
				Thread.sleep(500);
		}
		LOG.info("消费完毕数据：1s后 向{}取数据" , Constants.DATA_CONTROLLER_URL);
		Thread.sleep(1000);
		String str = getStrByService();
		JSONArray array = null ;
		if (str != null && !str.equals("null") && str.length() != 0) {
			try {
				array = new JSONArray(str);
			} catch (JSONException e) {
				LOG.info("",e);
				e.printStackTrace();
			}
//			System.out.println(str);
		} else {
			LOG.info("取到数据为空 ！");
		}
		for (int i = 0; i < array.length(); i++) {
			try {
				JSONObject obj = array.getJSONObject(i);
				T a = getAEntity(obj.toString());
				if(a==null){
					LOG.info("得到"+ a.getClass().getName()+ " 错误 !!!");
					throw new IOException(" 得到种类错误  ");
				}
				totalRawsCount.incrementAndGet();
				while( !container.offer(a,3,TimeUnit.SECONDS) ){
					LOG.info(" 任务 插入错误  : 队列已满：" + container.size());
					Thread.sleep(3000)	;
				};
			} catch (Exception e) {
				e.printStackTrace();
				LOG.error("数据读取：" , e);
			}
		}
		LOG.info("WebService 》查出原始记录[" + array.length() + "/" + totalRawsCount + "]条 ，原始容器中当前共有[" + container.size() + "]条记录");
	}

	abstract T getAEntity(String json);

	public void start(int threadNum) {
		for (int i = 0; i < threadNum; i++) {
			Thread thread = new Thread(new Runnable() {
				@Override
				public void run() {
					while (true) {
						try {
							insertToContainer();
						} catch (Exception e) {
							LOG.error("",e);
						}
					}
				}
			}, this.getClass().getSimpleName() + i);
			thread.start();
		}
	}

	public void start() {
		start(1);
	}
}
