package edu.buaa.nlp.util.segment;

import edu.buaa.inter.IWordSegmentor;
import edu.buaa.inter.WordSegmentFactory;

/**
 * HMM分词，代码由巢老师提供
 * @author Vincent
 * 需要如下资源文件
 * 	文件：data/Pos.txt data/userdict.dat data/Word.txt
 */
public class SegmentorHMM {

	private IWordSegmentor segmentor=null; 
	
	public SegmentorHMM() {
		WordSegmentFactory factory = new WordSegmentFactory();
		segmentor = factory.getWordSegmentor();
	}
	
	public String seg(String text){
		if(text==null || "".equals(text)) return "";
		return segmentor.WordSegment(text);
	}
}
