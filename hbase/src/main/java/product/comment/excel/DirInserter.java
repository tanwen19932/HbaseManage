package product.comment.excel;

import java.io.File;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import tw.utils.PropertiesUtil;

public abstract class DirInserter {
	
	static Properties properties ;
//	static final String FILEPATH = "电商.properties";
	static final String FILEPATH = "手机.properties";
	static final ExecutorService executorService = Executors.newFixedThreadPool(2);
	static {
		
		properties = PropertiesUtil.getProp(FILEPATH);
	}
	public void insertDir(String dir){
		File file = new File(dir);
		insertDir(file);
		while(true){
			if(executorService.isTerminated()){
				return ;
			}
			try {
				Thread.sleep(10*1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	private void addToThreadPool(File file) {
		executorService.execute(new  Runnable() {
			public void run() {
				if (properties.containsKey( file.getName() )) {
					System.out.println(file.getName()+"已经导入！");
					return ;
				}
				insertFile(file);
				properties.setProperty(file.getName(), "1");
				PropertiesUtil.saveProp(properties, FILEPATH);
				System.out.println(file.getName()+"导入成功！");
			}
		});
//		insertFile(file);
	}
	
	public void insertDir(File file){
		if( file.isDirectory() ){
			File[] files = file.listFiles();
			for(File tempFile : files){
				if(tempFile.isDirectory()){
					insertDir(tempFile);
				}
				else {
					addToThreadPool(tempFile);
				}
			}
		}
		else{
			addToThreadPool(file);
		}
	}
	
	public abstract void insertFile(File file);
	public void init() {
	}		
}
