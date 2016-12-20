
import edu.buaa.nlp.TextClassification.ThemeClassifier;
import edu.buaa.nlp.text.inter.ISummerizer;
import edu.buaa.nlp.text.process.summ.LexRankSummarizer;
import edu.buaa.nlp.util.segment.SegmentorHMM;

public class classifierTest {
	/*
	 * this.categoryId = categoryId;
		switch (categoryId) {
		case 1:
			this.categoryName = "政治";
			break;
		case 2:
			this.categoryName = "经济";
			break;
		case 3:
			this.categoryName = "文化";
			break;
		case 4:
			this.categoryName = "体育";
			break;
		case 5:
			this.categoryName = "军事";
			break;
		case 6:
			this.categoryName = "社会";
			break;
		case 7:
			this.categoryName = "科技";
			break;
		case 8:
			this.categoryName = "综合";
			break;
	 */
	public static void main(String[] args) {
		String text1 = "央视网消息：长江江豚在长江流域生活已有20万年，是唯一在长江中栖息的鲸类动物，被称为“微笑天使”。目前，它的种群数量只有约1040头。据江豚研究人员分析，环境污染、非法捕捞、采砂业和航运业，是威胁江豚生存的四大杀手。湖南岳阳东洞庭湖江豚保护区是全国第七个江豚保护区。近日，记者在保护区调查发现，非法捕捞依然存在。";

		String text = "国民党秘书长莫天虎自台“内政部”移民署退休未满3年，日前在向移民署递件申请登陆时遭驳回，莫天虎确定无法成行。" +

				"　“两岸和平发展论坛”将于11月2日举行，全国政协主席俞正声将在论坛前会见由詹启贤所率领的代表团。在论坛开幕典礼上，国台办主任张志军及詹启贤将分别致词。" +

				"　此次论坛分为5个组别与议题，包含“政治组”的政治互信与良性互动、“经济组”的新经济发展与两岸合作、“社会组”的深化两岸民众交流、“文化组”的文化传承与创新，以及“青年组”的探索新愿景与开创新前途。" +

				"　国民党内人士表示，洪秀柱不会出席论坛开幕式及张志军2日晚举行的晚宴，但会前往听两场论坛讨论。" +

				"　11月3日的论坛议程进行到中午，在主办单位致词后，结束1天半的论坛，下午参观首都博物馆，晚上安排欢送晚会。";
		ThemeClassifier themeClassifier = new ThemeClassifier();
		ISummerizer summerizer = new LexRankSummarizer();
		SegmentorHMM segmentor = new SegmentorHMM();
		text1 = segmentor.seg(text1);
		text = segmentor.seg(text);
		
		int result = themeClassifier.predict(text1, "zh");
		System.out.println(result);
		System.out.println(new ThemeClassifier().predict(text, "zh"));
	}
}
