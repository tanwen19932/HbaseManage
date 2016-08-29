package thread;
import java.io.Serializable;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;  
import java.util.concurrent.TimeUnit;  
  
public class TestThreadPool {  
  
    private static int produceTaskSleepTime = 2;  
      
    private static int produceTaskMaxNumber = 10;  
  
    public static void main(String[] args) {  
  
        // 构造一个线程池  
    	ExecutorService executorService = Executors.newCachedThreadPool();
    	
        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(1, 4, 3,  
                TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(3),  
                new ThreadPoolExecutor.DiscardOldestPolicy()); 
  
        for (int i = 1; i <= produceTaskMaxNumber; i++) {  
            try {  
                String task = "task@ " + i;  
                System.out.println("创建任务并提交到线程池中：" + task);  
                threadPool.execute(new TestThreadPool.ThreadPoolTask(task));
                String aString = null;
                Future<String> future= executorService.submit(new ThreadPoolTask(task),aString);
//              Thread.sleep(produceTaskSleepTime);  
            } catch (Exception e) {  
                e.printStackTrace();  
            }  
        }
        while(true){
        
        	System.out.println(threadPool.getActiveCount());
        	try {
				Thread.sleep(3000);
			} catch (Exception e) {
				// TODO: handle exception
			}
        }
    } 
   static class ThreadPoolTask implements Runnable, Serializable {  
    	  
        private Object attachData;  
      
        ThreadPoolTask(Object tasks) {  
            this.attachData = tasks;  
        }  
      
        public void run() {  
              
            System.out.println("开始执行任务：" + attachData);  
              
            attachData = null;  
        }  
      
        public Object getTask() {  
            return this.attachData;  
        }
    }
}  
