package edu.buaa.nlp.util.segment;


import org.chasen.crfpp.Tagger;

/**
 * CRF分词，模型采用CRF++训练，代码由李哲豪提供
 * 需要如下资源文件
 * 	文件夹：crf_models
 *  文件：crfppdll_javawraper_32.dll、crfppdll_javawraper_64.dll
 * @author Vincent
 *
 */
public class SegmentorCRF {
	public  Tagger tagger;

	/*static{
		tagger=new Tagger("-m crf_models/model4notag.model");
	}*/
/*	
	public Segmentor() {
	}*/

	public SegmentorCRF(boolean tagornot) {
		if (tagornot) {

		} else {
			tagger = new Tagger("-m crf_models/model4notag.model");
		}
	}

	/**
	 * 分词
	 * @param input 输入字符串
	 * @return 去掉首尾空白，输入字符串中的全角半角空格、制表符将被作为分词依据。返回每个词使用半角空格分隔
	 */
	public String seg(String input) {
		input = input.trim();
		String pure = input.replaceAll("[ \t　]+", "");
		StringBuilder sb = new StringBuilder();
		char[] states = this.getNoTagStates(pure);
		if (states.length == 1)
			return pure;
		else
			sb.append(pure.charAt(0));
		for (int i = 1, j = 1; i < states.length; i++, j++) {
			boolean hasSpace = false;
			while (input.charAt(j) == ' ' || input.charAt(j) == '　'
					|| input.charAt(j) == '\t') {
				j++;
				hasSpace = true;
			}
			if (hasSpace) {
				sb.append(" ");
			}
			if (states[i] == 'S') {
				if (!hasSpace)
					sb.append(' ');
				sb.append(pure.charAt(i));
			} else if (states[i] == 'B') {
				if (!hasSpace)
					sb.append(' ');
				sb.append(pure.charAt(i));
			} else if (states[i] == 'I') {
				if (states[i - 1] == 'E' || states[i - 1] == 'S') {
					if (!hasSpace)
						sb.append(' ');
				}
				sb.append(pure.charAt(i));
			} else {
				if (states[i - 1] == 'E') {
					if (!hasSpace)
						sb.append(' ');
				}
				sb.append(pure.charAt(i));
			}
		}
		return sb.toString();
	}

	/**
	 * 分词
	 * @param input 不含有全半角空格和制表符的字符串
	 * @return 返回每个词使用半角空格分隔
	 */
	public String segPure(String input) {
		// input=input.trim();
		input = input.replaceAll("[ \t　]+", "");
		StringBuilder sb = new StringBuilder();
		char[] states = this.getNoTagStates(input);
		if (states.length == 1)
			return input;
		else
			sb.append(input.charAt(0));
		for (int i = 1; i < states.length; i++) {
			if (states[i] == 'S') {
				sb.append(' ');
				sb.append(input.charAt(i));
			} else if (states[i] == 'B') {
				sb.append(' ');
				sb.append(input.charAt(i));
			} else if (states[i] == 'I') {
				if (states[i - 1] == 'E' || states[i - 1] == 'S') {
					sb.append(' ');
				}
				sb.append(input.charAt(i));
			} else {
				if (states[i - 1] == 'E') {
					sb.append(' ');
				}
				sb.append(input.charAt(i));
			}
		}
		return sb.toString();
	}

	private synchronized char[] getNoTagStates(String input) {
		tagger.clear();
		for (int i = 0; i < input.length(); i++) {
			tagger.add(input.charAt(i) + "");
		}
		tagger.parse();
		char[] states = new char[input.length()];
		for (int i = 0; i < input.length(); i++) {
			states[i] = tagger.y2(i).charAt(0);
		}
		return states;
	}

	public static void main(String[] args) {
		/*System.out.println(System.getProperty("sun.arch.data.model"));//当前java运行环境位数
		Segmentor seg = new Segmentor();
		// String input="迈向充满希望的新世纪——一九九八年新年讲话（附图片1张）";
		// String input="中共中央总书记、国家主席胡锦涛";
		String input = "    世\t界 上   　最\t遥远的距离不是生与死，而是我站在你面前，你 　却不知　道我爱你。";
//		input="<br/><br/><br/>PARIS (Reuters) - Customers braving the rush at Paris''s newest cafe to order their coffees and croissants, are now able to enjoy them in the company of a dozen resident cats.<br/><br/><br/><br/><br/> The &quot;Cafe des Chats&quot; in the heart of the capital''s chic Marais district is home to a dozen felines who weave in between the tables or curl up on armchairs as diners tuck in.<br/><br/> The establishment is aimed at Parisians unable to keep pets in cramped city-centre apartments and though the idea may seem eccentric, cafe manager Margaux Gandelon says the potential health benefits of &quot;purr therapy&quot; are real.<br/><br/> &quot;Purring produces vibrations which relieve arthritis and rheumatism, which lower your blood pressure and your heartbeat,&quot; Gandelon said.<br/><br/> This month''s opening weekend saw queues snaking along the pavement and bookings taken from now until November. Some 300 potential customers had to be turned away.<br/><br/> Gandelon says animal welfare is paramount and customers are prohibited from subjecting the cats to undue stress. She is prepared to evict any customers who fail to play by the rules although she admits she is more lenient with the animal residents: &quot;Cats are cats,&quot; she said.<br/><br/> The animals themselves are abandoned and stray cats adopted from pet rescue centres. Among them is Habby who suffers from feline dwarfism with a stunted tail and unusually short paws.<br/><br/> Despite two years spent with foster families the sweet-tempered tabby was never adopted but has now settled down to cafe life.<br/><br/> Visiting the cafe out of curiosity, business student Florian Laboureau described it as a &quot;great concept&quot;, but admitted he is more of a dog person.<br/><br/> (Reporting by Johnny Cotton; Editing by Mark John and Alison Williams)<br/> Pets";
		
		 * char []states=seg.getNoTagStates(input); for(int
		 * i=0;i<states.length;i++){ System.out.print(states[i]); }
		 * System.out.println();
		 
		System.out.println(seg.seg(input));
		System.out.println(seg.segPure(input));*/
		
		SegmentorCRF seg = new SegmentorCRF(false);
		System.out.println(seg.seg("系统真心不错"));
		
		
	}
}
