package com.mininglamp.nlp.languageDetect.impl;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by mxf on 16/11/7.
 * 字符到语言类型的映射
 */
public class Char2Lang {
	private Map<Character,List<String>> ch2langs = new TreeMap<>();

	public List<String> get(char ch){
		List<String> langs = ch2langs.get(ch);
		if (langs == null)
			return new LinkedList<>();
		else
			return langs;
	}
	public void loadFile(String file,String lang){
		try {
			LineIterator iterator = FileUtils.lineIterator(new File(file));
			while (iterator.hasNext()){
				String[] fields = iterator.nextLine().split("=");
				if (fields.length > 0 && fields[0].trim().length() > 0){
					char ch = fields[0].trim().charAt(0);
					if (ch2langs.containsKey(ch) == true)
						ch2langs.get(ch).add(lang);
					else{
						List<String> langs = new LinkedList<>();
						langs.add(lang);
						ch2langs.put(ch, langs);
					}
				}
			}
			iterator.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public static void main(String[] args){
		Char2Lang tt = new Char2Lang();
		tt.loadFile("resources/char/jp", "jp");
		tt.loadFile("resources/char/ko", "ko");
		tt.loadFile("resources/char/zh-cn", "zh-cn");
		tt.loadFile("resources/char/zh-tw", "zh-tw");

		System.out.println(tt.get('心'));
		String text = "野菜の価格高騰を理由に三重県鈴鹿市の教育委員会が来月と再来月の２日間、小学校などの給食を中止するとしたことについて、鈴鹿市は７日、「批判が多い」などとして中止の方針を撤回することを明らかにしました。野菜の価格が高騰していることを受けて、鈴鹿市教育委員会は、予算内で食材を確保するのが難しいとして、来月と来年１月の合わせて２日間、市立の３０の小学校と１３の幼稚園すべてで給食を中止するとしていました。これについて、鈴鹿市の末松則子市長は７日の記者会見で、「教育委員会が相談もなく決めたことで、批判も多い」などとして、中止の方針を撤回し、給食の提供を続ける考えを示しました。末松市長は「教育委員会には再検討を指示し回数を減らすことがないようにする。早い段階で相談があれば対応を検討できた。混乱を招いたことを深くおわびする」と述べました。この一方で、食材をどう確保するかについては、「学校給食は保護者からの給食費で提供することが法律で定められていて、公費は投入できず、保護者に追加で負担を求めるのも難しい。食材を調達する方法の見直しなども検討したい」と述べ、今月中旬までに対応策を示すとしています。";
		String text2 = "马云 ： 阿里巴巴明年开始将不再提 “电子商务 ” _财经 _解放网，马云：阿里巴巴明年开始将不再提“电子商务” 中青在线杭州10月13日(中国青年报·中青在线记者 董碧水)“‘电子商务’这个词可能很快就被淘汰，阿里巴巴从明年开始将不再提‘电子商务’这一说法。”这是阿里巴巴集团董事局主席马";
		System.out.println(LangRecog.getInstance().detect(text2));
	}
}
