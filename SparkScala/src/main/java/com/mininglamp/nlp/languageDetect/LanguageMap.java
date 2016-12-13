package com.mininglamp.nlp.languageDetect;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

import java.io.File;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by mxf on 16/11/1.
 */
public class LanguageMap {
	private Map<String,String> en2zh = new TreeMap<>();
	private final String name = "resources/language.properties";

	public String getZhName(String nameEn){
		String v = en2zh.get(nameEn);
		if (v == null)
			return "其他";
		else
			return v;
	}

	private void init(){
		try {
			LineIterator iterator = FileUtils.lineIterator(new File(name));
			while (iterator.hasNext()){
				String line = iterator.nextLine();
				String[] fields = line.split("\t");
				if (fields.length > 1){
					en2zh.put(fields[1].trim(), fields[0].trim());
				}
			}
			iterator.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public LanguageMap(){
		init();
	}

	public static void main(String[] args){
		LanguageMap tt = new LanguageMap();
		System.out.println(tt.getZhName("zh"));
		System.out.println(tt.getZhName("en"));
		System.out.println(tt.getZhName("ko"));
	}
}
