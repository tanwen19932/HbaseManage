package test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Job {

	private double startId;
	private static final String lock="sslock";

	public static void main(String[] args) {
		File file1=new File("C:\\Users\\Vincent\\Desktop\\testId.txt");
		File file2=new File("C:\\Users\\Vincent\\Desktop\\testId2.txt");
		BufferedReader br=null;
		try {
			br=new BufferedReader(new FileReader(file1));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		String line="";
		List<String> lines=new ArrayList<String>();
		try {
			while((line=br.readLine())!=null){
				lines.add(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			br=new BufferedReader(new FileReader(file2));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		List<String> lines2=new ArrayList<String>();
		try {
			while((line=br.readLine())!=null){
				lines2.add(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		for(int i=0; i<lines.size(); i++){
			if(!lines2.contains(lines.get(i))){
				System.out.println(lines.get(i));
			}
		}
	}
}
