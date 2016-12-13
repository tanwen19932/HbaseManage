package test;

import java.util.concurrent.BlockingQueue;

class Consumer implements Runnable{
	
	private BlockingQueue<String> queue;
	private String lock="lock1";

	public BlockingQueue<String> getQueue() {
		return queue;
	}

	public void setQueue(BlockingQueue<String> queue) {
		this.queue = queue;
	}

	@Override
	public void run() {
//		synchronized (lock) {
			while(queue.isEmpty()){
				//					lock.wait();
				System.out.println("暂无数据");
			}
			try {
				System.out.println("queue:"+queue.size()+" "+queue.take());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
//		}
	}
	
}
