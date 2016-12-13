package handler;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import getter.NewsGetter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class HandlerThread <T>{
	BlockingQueue<T> queue ;
	public AtomicLong count = new AtomicLong();
	Long lastTime;
	Class<? extends HandlerChain<T>> handlerClass;
	protected static Logger LOG = LoggerFactory.getLogger(HandlerThread.class);
	public HandlerThread(BlockingQueue<T> queue ,Class<? extends HandlerChain<T>> handlerClass) {
		this.queue = queue;
		this.handlerClass = handlerClass;
	}
	public void start() {
		start(1);
	}
	public void start(int threadNum ) {
		lastTime=System.currentTimeMillis();
		ExecutorService pool = Executors.newFixedThreadPool(threadNum,new ThreadFactoryBuilder()
				.setNameFormat(this.getClass().getSimpleName()).build());
		for (int i = 0; i < threadNum; i++) {
			LOG.info("启动 Handler 线程 线程 {}"  , i);
			pool.execute(new Runnable() {
				@Override
				public void run() {
					HandlerChain<T> handlerChain = null;
					try {
						handlerChain = handlerClass.newInstance();
					} catch (InstantiationException e1) {
						e1.printStackTrace();
					} catch (IllegalAccessException e1) {
						e1.printStackTrace();
					}
					while(true){
						try {
							T a = queue.poll(1, TimeUnit.SECONDS);
							if(a == null){
								LOG.info(   " 线程{}取到数据为null！ 队列大小：{}   等待1s钟后重试"  , Thread.currentThread().getName() ,queue.size() );
								Thread.sleep(1*1000);
								continue;
							}
							count.incrementAndGet();
							if(count.get()%1000==0){
								LOG.info("处理了{}/{}条数据,1000条花费时间 :{}ms 当前队列个数{}",
										count.get(),NewsGetter.totalRawsCount.get(),
										(System.currentTimeMillis()-lastTime) ,queue.size());
								lastTime = System.currentTimeMillis();
							}
							handlerChain.handleByChain(a);
//							LOG.debug("修复数据： "+a);
							a = null;
						} catch (InterruptedException e) {
							LOG.info(Thread.currentThread().getName()+" 读取Containler遇到错误！等待3s钟后重试" );
							LOG.error("",e);
						}
						catch (Error e) {
							LOG.error("ERROR :",e);
							System.exit(-1);
						}
					}
				}
			});
		}
		
	}
}
