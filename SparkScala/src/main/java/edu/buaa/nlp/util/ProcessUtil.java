package edu.buaa.nlp.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class ProcessUtil {

	public static String OS_TYPE_LINUX="linux";
	public static String OS_TYPE_WINDOWS="windows";
	public static String OS_TYPE_UNIX="unix";
	
	public static String getOSType(){
		String name=System.getProperty("os.name");
		if(name.contains("Windows")) return OS_TYPE_WINDOWS;
		else if(name.contains("Linux")) return OS_TYPE_LINUX;
		return name;
	}
	
	public static String getPID(){
		String name=ManagementFactory.getRuntimeMXBean().getName();
		return name.split("@")[0];
	}
	
	public static void killProcess(String pid) throws IOException{
		String type=getOSType();
		if(type.equalsIgnoreCase(OS_TYPE_LINUX)){
			killLinux(pid);
		}else if(type.equalsIgnoreCase(OS_TYPE_WINDOWS)){
			killWindows(pid);
		}
	}
	
	private static void killWindows(String pid) throws IOException{
		final String[] arr={"taskkill.exe", "-F", "-PID", pid};
		Runtime.getRuntime().exec(arr);
	}
	
	private static void killLinux(String pid) throws IOException{
		final String[] arr={"kill", "-9", pid};
		Runtime.getRuntime().exec(arr);
	}
	
	public static void killProcess() throws IOException{
		killProcess(getPID());
	}
	
	public static String startProcess(String[] command) throws IOException{
		StringBuilder sb = new StringBuilder();
//		final String[] command={"nohup ./run.sh &"};
		Process process = Runtime.getRuntime().exec(command);
		BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
		String line = null;
		while ((line = reader.readLine()) != null) {
			sb.append(line);
		}
		process.getOutputStream().close();
		reader.close();
		process.destroy();
		return sb.toString();
	}
	
	public static void main(String[] args) {
		Thread thread1=new Thread(new Runnable() {
			@Override
			public void run() {
				while(true){
					System.out.println(Thread.currentThread().getName()+"-running...");
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				
			}
		});
		Thread thread2=new Thread(new Runnable() {
			@Override
			public void run() {
				while(!Thread.currentThread().isInterrupted()){
					System.out.println(Thread.currentThread().getName()+"-running...");
					try {
						Thread.sleep(105);
					} catch (InterruptedException e) {
						System.out.println("interputting..");
					}
				}
				
			}
		});
		thread2.start();
		Thread thread3=new Thread(new Runnable() {
			@Override
			public void run() {
				while(true){
					System.out.println(Thread.currentThread().getName()+"-running...");
					try {
						Thread.sleep(110);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				
			}
		});
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println("hook...");
				ProcessBuilder builder=new ProcessBuilder("nohup","./run.sh","&");
				StringBuilder sb = new StringBuilder();
				try {
					Process process=builder.start();
					BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
					String line = null;
					while ((line = reader.readLine()) != null) {
						sb.append(line);
					}
					process.getOutputStream().close();
					reader.close();
					process.destroy();
				} catch (IOException e) {
					e.printStackTrace();
				}
				System.out.println("restart:"+sb.toString());
			}
		}));
		
		int N=3;
		long initDelay=1;
		long delay=60*N;
		ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
		executorService.scheduleWithFixedDelay(new Runnable() {
			@Override
			public void run() {
				System.out.println("going to die...");
				Map<Thread, StackTraceElement[]> map=Thread.getAllStackTraces();
				for(Thread key:map.keySet()){
					if(key.getName().equals("Thread-1")){
						while(!key.isInterrupted()){
							key.interrupt();
						}
					}
				}
//				Runtime.getRuntime().exit(0);
				try {
					killProcess();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}, initDelay, delay, TimeUnit.MINUTES);
		thread1.start();
		thread3.start();
	}
}
