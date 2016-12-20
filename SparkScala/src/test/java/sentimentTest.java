
import edu.buaa.nlp.sentiment.orientation.CombineSentimentAnalysis;
import edu.buaa.nlp.text.inter.ISentimentAnalysis;
import edu.buaa.nlp.util.segment.SegmentorHMM;

public class sentimentTest {
	public static void main(String[] args) {
		String text = "国民党秘书长莫天虎自台“内政部”移民署退休未满3年，日前在向移民署递件申请登陆时遭驳回，莫天虎确定无法成行。" +
	
				"　“两岸和平发展论坛”将于11月2日举行，全国政协主席俞正声将在论坛前会见由詹启贤所率领的代表团。在论坛开幕典礼上，国台办主任张志军及詹启贤将分别致词。" +
	
				"　此次论坛分为5个组别与议题，包含“政治组”的政治互信与良性互动、“经济组”的新经济发展与两岸合作、“社会组”的深化两岸民众交流、“文化组”的文化传承与创新，以及“青年组”的探索新愿景与开创新前途。" +
	
				"　国民党内人士表示，洪秀柱不会出席论坛开幕式及张志军2日晚举行的晚宴，但会前往听两场论坛讨论。" +
	
				"　11月3日的论坛议程进行到中午，在主办单位致词后，结束1天半的论坛，下午参观首都博物馆，晚上安排欢送晚会。";
		 SegmentorHMM segmentor = new SegmentorHMM();
		 ISentimentAnalysis a= new CombineSentimentAnalysis();
		 int se = a.analyseParagraph(segmentor.seg(text), "zh") ;
		 System.out.println(se);
	}
}

