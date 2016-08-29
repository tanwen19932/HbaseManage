package news.cisionNews.hbase;

import java.io.File;
import java.io.FileWriter;
import tw.utils.HttpUtil;
public class CisionBlogDown extends CisionAPI{
	@Override
	public void runByThread() {

		int retryTimes = 0;
		boolean retry = false;
		String task = null;
		File file = null;
		while (true) {
			try {
				if (!retry || task == null)
					task = taskStart(taskList);
				else if (retryTimes >= 10 && task != null) {
					// deleteUndoTasks(task);
					retryTimes = 0;
					retry = false;
					task = taskStart(taskList);
				}
				if (task == null) {
					Thread.sleep(3000);
					continue;
				}

				file = new File(CisionAPIDir + task.replaceAll("[:]", "_") + ".txt");
				if (retryTimes == 0 && file.exists()) {
					deleteUndoTasks(task);
					retry = false;
					retryTimes = 0;
				}
				System.out.println("开始任务：" + task);
				String requestUrl = requestUrlPrefix.replaceAll("TIME", task);
				
//				String content = HttpUtil.getHttpContent("http://195.23.32.196/wiseapibulk/rest.svc/socialmedia/XML/4c7e54f6-57a5-43d6-9528-b6ad98d1ed52?index=1000");
				String content = HttpUtil.getHttpContent( requestUrl );
				int tempCount = 0;
				if (content == null || content.equals("")) {
					taskList.add(task);
					doingTask.add(task);
					try {
						Thread.sleep(10*1000);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
//				} else if ( (tempCount = check(content)) == 0 ) {
//					taskList.add(task);
//					doingTask.add(task);
				} else {
					todayCount.addAndGet(tempCount);
					file.createNewFile();
					FileWriter fileWriter = new FileWriter(file);
					fileWriter.write(content);
					fileWriter.close();
//					Thread.sleep(new Random().nextInt(3000));
					deleteUndoTasks(task);
				}
			} catch (Exception e) {
				e.printStackTrace();
				retry = true;
				retryTimes++;
				System.err.println(task + "任务失败 重试" + retryTimes);
				continue;
			}
		}
	}


	public static void main(String[] args) {
		CisionBlogDown CisionBlog = new CisionBlogDown();
		CisionBlog.setDayLimit(600000);
		CisionBlog.requestUrlPrefix = "http://195.23.32.196/wiseapibulk/rest.svc/blogsongoing/XML/2d488b2d-6af3-4992-b310-d7a7788c600b?since=TIME&interval=1";
		CisionBlog.resultBulkXML = "GetBlogsOngoingXMLResult";
		CisionBlog.start();
	}
}
