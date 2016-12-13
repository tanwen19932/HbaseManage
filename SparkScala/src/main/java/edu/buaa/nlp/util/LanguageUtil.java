package edu.buaa.nlp.util;

import com.google.common.base.Optional;
import com.optimaize.langdetect.LanguageDetector;
import com.optimaize.langdetect.LanguageDetectorBuilder;
import com.optimaize.langdetect.i18n.LdLocale;
import com.optimaize.langdetect.ngram.NgramExtractors;
import com.optimaize.langdetect.profiles.LanguageProfile;
import com.optimaize.langdetect.profiles.LanguageProfileReader;
import com.optimaize.langdetect.text.CommonTextObjectFactories;
import com.optimaize.langdetect.text.TextObject;
import com.optimaize.langdetect.text.TextObjectFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

public class LanguageUtil {
	// load all languages:
	private static List<LanguageProfile> languageProfiles = null;
	// build language detector:
	private static LanguageDetector languageDetector = null;
	// create a text object factory
	private static TextObjectFactory textObjectFactory = null;
	private static final Logger LOG = LoggerFactory.getLogger(LanguageUtil.class);
	static {
		try {
			languageProfiles = new LanguageProfileReader().readAllBuiltIn();
			languageDetector = LanguageDetectorBuilder.create(NgramExtractors.standard()).withProfiles(languageProfiles)
					.build();
			textObjectFactory = CommonTextObjectFactories.forDetectingOnLargeText();
		} catch (IOException e) {
			LOG.error("系统初始化语言识别模块错误 ！");
			System.exit(-1);
		}
	}
	
	public LanguageUtil() throws IOException {
	}

	public static boolean isChinese(String content) {
		int sum = 0;
		int len = content.length();
		for (int i = 0; i <= len / 2; i++) {
			String s = content.charAt(i) + "";
			if (s.matches("[\u4E00-\u9FA5]"))
				sum++;
		}
		return (sum / (len * 0.5)) > 0.2 ? true : false;
	}

	public static String langDetect(String content) {
		try {
			TextObject textObject = textObjectFactory.forText(content);
			Optional<LdLocale> lang = languageDetector.detect(textObject);

			String langCode = null;
			if (lang.isPresent()) {
				langCode = lang.get().toString();
				if (langCode.equalsIgnoreCase("zh-CN")) {
					langCode = "zh";
				}
			} else {
				content = content.replaceAll("[0-9a-zA-Z\\.\\(\"\\)/,']", "");
				textObject = textObjectFactory.forText(content);
				lang = languageDetector.detect(textObject);
				if (lang.isPresent()) {
					langCode = lang.get().toString();
					if (langCode.equalsIgnoreCase("zh-CN")) {
						langCode = "zh";
					}
				}
			}
			return langCode;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void main(String[] args) throws IOException {
		System.out.println(isChinese("china & japan"));

		LanguageUtil langUtil = new LanguageUtil();

//		String text = "1.这类resource properties文件是由classloader装载的，和java文件很类似，所以应该把这类文件加到classpath中。";
//		text += "2. ResourceBundle.getBundle(\"com.nana.test.autogen\")告诉classloader装载一个资源，其名字是\"autogen\"，包名为\"com.nana.test";
//		text += "3. 如果想不写包名，直接获取autogen.properties，则需要把autogen.properties文件拷贝到classes/com/nana/test/目录下，和调用它的类文件一起。";
//		text = "일부 해외지역 출발 운임은 유류할증료 대신 항공사 부과금액을 포함하고 있습니다.상기 운임에 해당하는 예약 클래스에 잔여 좌석이 있을 때만 구매 가능하므로, 선택 날짜에 따라 구매 가능 여부가 달라질 수 있습니다.환불 및 예약 변경에 관련된 규정은 예약 단계 내 상세 운임규정에서 확인하실 수 있습니다.";
//		// text = PreProcessor.readFile("test/5.txt");
//		ExecutorService service = Executors.newFixedThreadPool(100);
//		int i = 9;
//		while (true) {
//			service.submit(new Runnable() {
//				@SuppressWarnings("static-access")
//				public void run() {
//					System.out.println(langUtil.langDetect(
//							"일부 해외지역 출발 운임은 유류할증료 대신 항공사 부과금액을 포함하고 있습니다.상기 운임에 해당하는 예약 클래스에 잔여 좌석이 있을 때만 구매 가능하므로, 선택 날짜에 따라 구매 가능 여부가 달라질 수 있습니다.환불 및 예약 변경에 관련된 규정은 예약 단계 내 상세 운임규정에서 확인하실 수 있습니다."));
//				}
//			});
//			service.submit(new Runnable() {
//				@SuppressWarnings("static-access")
//				public void run() {
//					System.out.println(langUtil.langDetect(
//							"3. 如果想不写包名，直接获取autogen.properties，则需要把autogen.properties文件拷贝到classes/com/nana/test/目录下，和调用它的类文件一起。"));
//				}
//			});
//			if (i++ > 100){
//				break;
//			}
//		}

		// text = PreProcessor.readFile("test/2.txt");
		// System.out.println(langUtil.langDetect(text));
		// text = PreProcessor.readFile("test/3.txt");
		System.out.println(edu.buaa.nlp.util.LanguageUtil.langDetect("出几台PSV1000 2000 psv勇者斗恶龙限定版"));
		// text = PreProcessor.readFile("test/4.txt");
		// System.out.println(langUtil.langDetect(text));

		// for(int i=1;i<200;i++)
		// {
		// String content = PreProcessor.readFile("test/docs/" + i + ".txt");
		// System.out.println(content);
		// System.out.println(langUtil.langDetect(content));
		// }
	}
}
