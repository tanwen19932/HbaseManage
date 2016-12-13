package com.mininglamp.nlp.languageDetect.impl;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * Created by mxf on 16/11/7.
 * 自己实现的汉语文本发现
 */
public class LangRecog {
	private static LangRecog instance = new LangRecog();
	private Char2Lang char2Lang = new Char2Lang();

	public static LangRecog getInstance(){
		return LangRecogHolder.instance;
	}

	private class IntValue{
		private int count;
		public IntValue(int count) {
			this.count = count;
		}
		public void setCount(int count) {
			this.count = count;
		}
		public int getCount() {
			return count;
		}

		@Override
		public String toString() {
			return "IntValue{" +
					"count=" + count +
					'}';
		}
	}
	public String detect(String text){
		Map<String,IntValue> lang2count = countLang(text);
		String langBest = evalLangClass(lang2count, text.length());
		return langBest;
	}
	private String evalLangClass(Map<String,IntValue> lang2count, int textLength){
		IntValue countJa = lang2count.get("ja");
		if (countJa != null){
			if (countJa.getCount()*4 >= textLength)
				return "ja";
		}

		Set<Map.Entry<String, IntValue>> entrySet = lang2count.entrySet();
		String langBest = "";
		int countBest = 0;
		for (Map.Entry<String, IntValue> entry: entrySet){
			if (countBest < entry.getValue().getCount()){
				countBest = entry.getValue().getCount();
				langBest = entry.getKey();
			}
		}
		if (countBest == 0 ){
			return langBest;
		}else if (countBest*8 >= textLength) {
			return langBest;
		}else {
			return "";
		}
	}
	private Map<String,IntValue> countLang(String text){
		Map<String,IntValue> lang2count = new TreeMap<>();
		for (int ii = 0,length = text.length(); ii < length; ii++){
			if (text.charAt(ii) != ' ') {
				List<String> langs = char2Lang.get(text.charAt(ii));
				pushLangCount(lang2count, langs);
			}
		}
		return lang2count;
	}
	private void pushLangCount(Map<String,IntValue> lang2count, List<String> langs){
		for (String lang: langs){
			IntValue value = lang2count.get(lang);
			if (value == null)
				lang2count.put(lang, new IntValue(1));
			else
				value.setCount(1+value.getCount());
		}
	}
	private static class LangRecogHolder{
		public static LangRecog instance = new LangRecog();
	}
	private void initData(){
		char2Lang.loadFile("resources/char/jp", "ja"); //日语
		char2Lang.loadFile("resources/char/ko", "ko");  //韩语
		char2Lang.loadFile("resources/char/zh-cn", "zh"); //简体汉语
		char2Lang.loadFile("resources/char/zh-tw", "zh-tw"); //繁体汉语
	}
	private LangRecog(){
		initData();
	}
}