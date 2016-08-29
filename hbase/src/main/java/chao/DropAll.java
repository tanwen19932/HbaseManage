package chao;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

import hbase.util.HbaseUtil;

public class DropAll {
	public void Mxbean(){

//		String command = "./hbase org.apache.hadoop.hbase.mapreduce.Export TABLE  hdfs://192.168.55.95:54310/hbase/TABLE/";
//		String count= "count 'TABLE'";
//		String[] tables = HbaseUtil.getTables();
//		for (String s : tables) {
//			System.out.println(count.replaceAll("TABLE", s));
////			HbaseUtil.deleteTable(s);
//		}
		RuntimeMXBean runtime = ManagementFactory.getRuntimeMXBean();
		int pid;
		String name = runtime.getName(); // format: "pid@hostname"\
		System.out.println(name);
		try {
			pid = Integer.parseInt(name.substring(0, name.indexOf('@')));
		} catch (Exception e) {
			pid = -1;
		}
		try {
			/*
			在windows:process = runtime.exec(new String[] { "cmd.exe","/C", "dir"});
			在linux:process = runtime.exec(new String[] { "/bin/sh","-c", "echo $PATH"});*/
//			String[] command = {"/bin/sh", "-c", "lsof -p " + pid + " | wc -l"};
			Process process = Runtime.getRuntime().exec(new String[] { "cmd.exe","/C", "dir"});
            
			System.out.print((process.getInputStream()));
			System.err.print((process.getErrorStream()));
              
            process.getOutputStream().close();
            
			System.out.println(pid);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	
	}
	public static void main(String[] args) throws Exception {
		String command = "./hbase org.apache.hadoop.hbase.mapreduce.Export TABLE  hdfs://192.168.55.95:54310/hbase/TABLE/";
		String count= "count 'TABLE'";
		String create = "create 'TABLE','F'";
		String[] tables = HbaseUtil.getTables();
		for (String s : tables) {
			System.out.println(create.replaceAll("TABLE", s));
//			HbaseUtil.deleteTable(s);
		}
//		Runtime.getRuntime().addShutdownHook(new Thread() {
//			public void run() {
//				System.err.println("FFFFFFFFFF");
//			}
//		});
//		while(true){
//			System.out.println("??????!!!!");
//		}
	}
}
