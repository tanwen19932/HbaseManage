package handler;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import controller.Constants;

public abstract class QueueHandler<T> implements Runnable {
	protected BlockingQueue<T> queue;
	protected int threadNum;
	int queueSize = 1000;

	private static final Logger LOG = LoggerFactory.getLogger(QueueHandler.class);
	protected ExecutorService threadPool;
	protected QueueHandler<T> next;
	protected Handler<T> handler;
	protected ErrorHandler<T> errorHandler;
	
	public QueueHandler() {
		threadNum = Constants.PREPROCESS_THREAD_NUM;
		queue = new LinkedBlockingQueue<>();
	}

	@Override
	public void run() {
		threadPool = Executors.newFixedThreadPool(threadNum);
		for (int i = 0; i < threadNum; i++) {
			threadPool.submit(new Runnable() {
				@Override
				public void run() {
					while (true) {
						try {
							T obj = queue.take();
							boolean isHandleSuccess = handler.handle(obj);
							if (isHandleSuccess) {
								errorHandler.successHandle(obj);
								next.add(obj);
							}
							else {
								errorHandler.errorHandle( handler.getClass().getSimpleName(), obj );
							}
						} catch (Exception e) {
							LOG.error(this.getClass().getName() + "处理线程 ： ", e);
						}
					}
				}
			});
		}
	}

	public void start(int threadNum) {
		if (handler == null) {
			LOG.error(" 未设置Handler " + this.getClass().getSimpleName());
			System.exit(-1);
		}
		this.threadNum = threadNum;
		new Thread(this).start();
		if (next != null) {
			next.start(threadNum);
		}
	}

	public void add(T param) {
		try {
			while (queue.size() > queueSize) {
				Thread.sleep(1000);
			}
			queue.offer(param, 3, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			LOG.error("保存错误：", e);
		}
	}
	public int getQueueSize() {
		return queueSize;
	}

	public void setQueueSize(int queueSize) {
		this.queueSize = queueSize;
	}

	public QueueHandler<T> getNext() {
		return next;
	}

	public void setNext(QueueHandler<T> next) {
		this.next = next;
	}

	public Handler<T> getHandler() {
		return handler;
	}

	public void setHandler(Handler<T> handler) {
		this.handler = handler;
	}

	public void setThreadNum(int threadNum) {
		this.threadNum = threadNum;
	}
}
