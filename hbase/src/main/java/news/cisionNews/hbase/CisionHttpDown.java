package news.cisionNews.hbase;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Random;
import java.util.TreeSet;

import tw.utils.FileUtils;
import tw.utils.HttpUtil;

public class CisionHttpDown extends CisionAPI {
//	@Override
//	public synchronized String taskStart(List<String> taskList) throws Exception {
//		String task;
//		if (!taskList.isEmpty()) {
//			int i = 0;
//			while (true) {
//				task = taskList.remove(0);
//				String day = task.substring(0, 10);
//				if (day.compareTo("2016-07-24") >= 0 && day.compareTo("2016-07-26")<0) {
//					return task;
//				} else {
////					if (i == 2)
//					deleteUndoTasks(task);
//					continue;
////					i++;
//				}
//				// System.out.println(task.substring(beginIndex));
//			}
//		}
//		return null;
//	}

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
					return;
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

	// System.out.println(content);
	// if( content==null || content.equals("")){
	// taskList.add(task);
	// doingTask.addElement(task);
	// }else{

	// file.createNewFile();
	// FileWriter fileWriter = new FileWriter(file);
	// fileWriter.write(content);
	// fileWriter.close();
	// Thread.sleep(new Random().nextInt(3000));
	// deleteUndoTasks(task);
	// }

	public static void main(String[] args) {
		CisionHttpDown cisionHttp2 = new CisionHttpDown();
		cisionHttp2.requestUrlPrefix = "http://wiseapiproxy.azurewebsites.net/xml/13b860b0-7c69-47fa-8a9f-482d638c32e0/TIME/1";
		cisionHttp2.setDayLimit(500000);
		cisionHttp2.start();
	}
	
}
