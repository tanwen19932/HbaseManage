package edu.buaa.nlp.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CharUtil {
	
	private static String regPuncEx="\"《》[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？_ ]";
	private static Pattern patPunc = Pattern.compile(regPuncEx); 
	
	/**
	 * 对中英文混合情况汉字汉字之间空格，对英文不处理，对中按字切分
	 * @param input
	 * @return
	 */
	public static String addSpaceInChinese(String input){
		if(input == null){
			return "";
		}
		Pattern pattern = Pattern.compile("(?=[\u4E00-\u9FA5])(?=[\u4E00-\u9FA5])");
		Matcher matcher = pattern.matcher(input);
		if(matcher.find()){ 
			input = matcher.replaceAll(" ");
		}
		input = input.replaceAll("\\s{2,}", " ").trim();
		return input;
	}
	
	public static String removeUselessChar(String input){
		if(input == null){
			return "";
		}
		
		Matcher matcher = patPunc.matcher(input);
		if(matcher.find()){ 
			input = matcher.replaceAll("").trim();
		}
		return input;
	}
	
	
	/**
	 *  对中英文混合情况去除汉字之间空格，对英文不处理
	 * @param input
	 * @return
	 */
	public static String removeSpaceInChinese(String input){
		if(input == null || "".equals(input)){
			return "";
		}
		Pattern pattern = Pattern.compile(" (?=[\u4E00-\u9FA5])");
		Matcher matcher = pattern.matcher(input);
		if(matcher.find()){ 
			input = matcher.replaceAll("");
		}
		return input;
	}
	
	/***
	 * 去掉英文中的空格，主要是这种情况： Tiongkok .  sejati( superfan) ” kicau@ Manorracing  ’ s 
	 * @param input
	 * @return
	 */
	public static String removeSpaceInEnglish(String input){
		if(input == null || "".equals(input)){
			return "";
		}
		
		input += " ";
		input = input.replaceAll("\\s+\\.\\s+", ". ");
		input = input.replaceAll("\\(\\s+([0-9a-z])", "($1");
		input = input.replaceAll("@\\s+([0-9a-zA-Z])", "@$1");
		
		input = input.replaceAll("\\s+'\\s*s\\s+", "'s ");
		
		input = input.replaceAll("\\s+([”|’])", "$1");
		input = input.replaceAll("/\\s+", "/");
		return input;
	}
	
	
	public static void main(String[] args) {
/*		String text="Apple and the FBI Break the Fourth Wall In their ongoing clash, the two notoriously secret organizations are fighting for Americans hearts and minds. There ’ s a recurring moment in political debates when a candidate, eager to speak directly to the American public, shifts his gaze from the moderators and opponents and talks straight into the camera. The move is a little jarring — especially when Chris Christie deploys his unblinking stare — but it ’ s effective: It says, “ Forget these other candidates; I ’ m talking to you, voter. ” In their very public fight over device security, Apple and the FBI seem to employing this same tactic. Instead of mining the tech industry or outside experts for support, the two organizations, both known for their secrecy, are breaking the fourth ";
		
		text = "Kabar ' s pembalap datang dari Rio Haryanto di Tiongkok . Tim oranye itu hanya menyebut bocah berambut ikal pirang itu adalah penggemar sejati( superfan) dari Rio Haryanto . "; 
		text +=	" Rio menemui sang superfan, Milo  tepat setelah setelah hujan reda, ” kicau@ Manorracing di Twitter, Sabtu( 16/ 4/ 2016) ." ;
		System.out.println(removeSpaceInEnglish(text));*/
		
		String str = "*\"adCVs*34_a _09_b5*[/435^*&城池()^$$&*).{}+.|.)%%*(*.中国}34{45[]12.fd'*&999下面是中文的字符￥……{}【】。，；’“‘”？";
		System.out.println(str);
		System.out.println(removeUselessChar(str));
	}
}
