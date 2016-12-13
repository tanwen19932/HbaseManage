package test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.buaa.nlp.TextClassification.ThemeClassifier;
import edu.buaa.nlp.sentiment.orientation.CombineSentimentAnalysis;
import edu.buaa.nlp.text.process.factory.TextProcessFactory;
import edu.buaa.wordsegment.PreProcessor;



public class testClassify implements Runnable {

	
	public testClassify()
	{

	}
	
	
	
	@Override
	public void run() {
		String text = "在8月21日举办的HackPWN安全极客狂欢节上，国内知名越狱团队“盘古”，就现场上演了如何利用iOS 8.4.1漏洞，对手机进行越狱等高权限操作。\n" +
				"iOS一直是大众眼中最安全的手机系统(黑莓哭瞎)，但实际上iOS中也存在着许多漏洞。在8月21日举办的HackPWN安全极客狂欢节上，国内知名越狱团队“盘古”，就现场上演了如何利用iOS 8.4.1漏洞，对手机进行越狱等高权限操作。\n" +
				" 图1：盘古团队现场演示越狱过程 \n" +
				"  盘古团队是“盘古越狱”的发起者，也是挖掘到最多苹果漏洞的中国团队。此次在HackPWN大会上，盘古团队的代表在演示越狱过程前表示，漏洞不仅可以用于越狱，还能通过绕过内核来获取更高的使用权限，其中就包括窃取机主个人资料。\n" +
				"  整个越狱过程在3分钟左右，在越狱过程中盘古团队表示：“漏洞是一个中性的东西，我们越狱只是为了让用户取得更多的开放权力，以及一定的个性化定制。”之后盘古团队又演示了如何在越狱后iPhone6中任意安装APP。\n" +
				"  在数日前，苹果刚刚为iOS发布了安全性更新，并修复了大量漏洞;然而仅仅在iOS8.4.1系统推出后不久，盘古就推出了此项越狱方法。对此，黑客评委表示，“盘古作为中国最知名的越狱团队，在iOS新系统刚刚推出后就找到了漏洞并实现越狱，其执着的骇客精神十分值得学习。\n" +
				"     图2：360团队于HackPWN上自爆身家\n" +
				" 据了解，HackPWN大会是由安全团队360VulcanTeam、 360UnicornTeam共同发起，旨在通过挖掘漏洞的形式，提高智能设备的安全性，给广大智能设备用户创造一个安全的“物联网”使用环境。在此次HackPWN大会上，十余位国际知名黑客亲临现场，共同参与了对汽车、智能烤箱、智能手环等设备的破解，并对其破解过程与原理加以详细讲解。";
//		text = PreProcessor.readFile("err1.txt");
//		text = "AngularJS lets you write client-side web applications as if you had a smarter browser. It lets you use good old HTML (or HAML, Jade and friends!) as your template language and lets you extend HTML’s syntax to express your application’s components clearly and succinctly. It automatically synchronizes data from your UI (view) with your JavaScript objects (model) through 2-way data binding. To help you structure your application better and make it easy to test, AngularJS teaches the browser how to do dependency injection and inversion of control.";
		//CombineSentimentAnalysis analyse = new CombineSentimentAnalysis();
		ThemeClassifier classifer = new ThemeClassifier();
//		for(int i = 0; i < 10; i++){
			 long s = System.currentTimeMillis();
			 int r = classifer.predict(text,"cn");
			 //int r= analyse.analyseParagraph(text, "en");
			 long t = System.currentTimeMillis();
			 System.out.println(r + "  " + (t-s));
//		}
		
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//System.out.println(WordSegmentFactory.getWordSegmentor().WordSegment("国家旅游局有关负责人12日表示，错峰休假、弹性作息不是要缩短每周法定工作时间、周五下午直接安排放假。")); //航天的发展与空间探索基于性能的导航 hello world"));
		
		String str = "新华社照片，路透，2006年5月8日    [体坛聚焦]（1）足球――齐达内告别皇马    5月7日，皇家马德里队球员齐达内深情地向球迷挥手致意。当日，在西班牙足球甲级联赛第三十七轮比赛中，皇家马德里队主场以3比3战平比利亚雷亚尔队，这场比赛也是法国老将齐达内在皇马主场博纳乌球场的告别赛，他在比赛第66分钟接到队友贝克汉姆的妙传头球破网，为自己5年的皇马生涯画上了圆满的句号。    新华社/路透";
		String str1 = "AngularJS lets you write client-side web applications as if you had a smarter browser. It lets you use good old HTML (or HAML, Jade and friends!) as your template language and lets you extend HTML’s syntax to express your application’s components clearly and succinctly. It automatically synchronizes data from your UI (view) with your JavaScript objects (model) through 2-way data binding. To help you structure your application better and make it easy to test, AngularJS teaches the browser how to do dependency injection and inversion of control.";
		String str2 = "政治 体制 改革 的 目标 ， 是 按照 民主化 和 法制化 紧密 结合 的 要求 ，努力 建设 社会主义 的 民主 政治。";
                String str3="2016世界经济论坛年会(冬季达沃斯)20日在瑞士达沃斯开幕，阿里巴巴董事局主席马云等中国企业家参会。官网资料显示，马云此次参会除了作为B2O中小企业组主席参加今年在杭州举行的G20会议筹备会之外，几乎没有在公开的场合亮相。 　　不过据达沃斯会议主办方透露，马云在达沃斯行程密集，几乎是按15分钟一次会见在安排。 　　达沃斯媒体众多，马云出入会议中心不时被追着抓拍，并不断向各国政要和商界领袖们力推中国新经济和阿里巴巴全球化。 　　每年参加达沃斯，马云必去的场合是青年全球领袖的会议，今年也不例外，马云唯一的一场演讲特意为年轻人开讲。 　　哈萨克斯坦总理一个月前到访了阿里巴巴园区，邀请马云做总理顾问。在达沃斯马云又去了总理的住处，据说商谈的内容是推动“一带一路一网”。 　　加拿大总理贾斯汀杜鲁多昨天下午在个人twitter上贴出了和马云见面的照片，并@给阿里巴巴的官方twittter，说这是“一次关于贸易基础设施和推动企业创新的很好的讨论”。 　　另外，中国国家副主席李源潮在达沃斯也会见了马云等中国企业家，并称赞在淘宝买的皮鞋保暖好、且价格非常实惠。";
                String str4="受到撤职另行分配待遇较低的工作处分的，刘时平由原来的１３级降到１７级；裴达由原来的１３级降到１８级；吕建中、林钢都由原来的１５级降到１８级；赵克惠由１８级降到２１级；朱克潜和蒋如都由１７级降到２１级。　　受到降职降级降薪处分的，孙乃由原来的１７级降到１９级；陈国安由原来的２０级降到２１级；方达由原来的２１级降到２２级；张宝义由原来的教员９级降到１０级。　　所有右派分子，党员一律开除党籍，团员一律开除团籍，处理以后，都要放到体力劳动中去改造。受第三类和第三类以下处分的名为劳动锻炼。　　４月８日，大部分右派分子到唐山柏各庄农场劳动改造。计有：蒋元椿、林钢、李右、胡平、方达、刘晓唏、赵克惠、张恩铭、刘时平、吕建中、裴达、刘衡、孙乃、田兰坡。　　４月份，又挖出右派分子３人：钦达木尼（由内蒙古处理）、黄操良（自杀）、胡骑，受降职降级降薪处分。８月，记者部补课，又挖出３名右派分子：高粮，撤销原有职务实行留用查看，不定级，每月６０元。季音，撤消原有职务另行分配待遇较低的工作，由１１级降到１６级。习平，降职降级降薪，由１６级降到１８级。　　１９５８年１１月６日，季音、高粮、胡骑和蓝翎也到柏各庄农场劳动。　　１９５９年春，因我不服罪，由第三类处分降为第二类。即由劳动锻炼改为监督劳动，每月２６元。　　１９５９年秋，裴达、杨春长、田兰坡摘掉帽子，杨春长已在报社，裴、田回到报社。　　１９６１年，我们全部回到报社。其他右派纷纷摘掉帽子，只我一人因不服罪，没有摘帽。　　除了蒋元椿、胡骑、裴达、刘晓唏、季音、习平、田兰坡、朱克潜、杨春长和我留报社外，其他人分到内蒙古、河南、新疆、贵州等地。　　１９７８年１２月８日开始，报社３０名右派分子陆续全部获得改正。事实证明，所有人的言论不但没有“三反”罪状，而且绝大多数完全正确。　　调往外地的刘时平、蒋如芝、林钢、吕建中、高粮、蓝翎、沈同衡、孙乃、李右、赵克惠先后回到报社。延伸阅读：邓拓的本来面目　 钟叔河：真“右派”的四十八条来源：[文化先锋]上一页;[1];[2];[3];[4];[5];[6];[7];[8];[9];";
                String str5="原先，市场预计中国央行对境外金融机构在境内金融机构存放离岸人民币执行正常存款准备金率政策，将大幅缩减离岸人民币流动性。但26日当天香港隔夜人民币Hibor突然收报1.1445%，创2014年5月8日以来的最低值，表明部分银行为了缴纳存款准备金而吸收过多离岸人民币，反而造成一部分资金的闲置，给投机资本低成本沽空人民币创造机会。";
                String str6="长江 证券 资深 投顾 李凯 在 接受 财 新 记者 采访 时 表示 ， 本周 市场 再次 进入 暴跌 模式 ， 相 较 上 一轮 ， “ 黑天鹅 ” 频现 、 被动性 抛盘 增大 等 成为 新 的 做 空 元素 ， 上周 以来 ， 索罗斯 做 空 中国 的 言论 成为 媒体 关注 的 焦点 ， 农行 38 亿 票据 案 则 使 违规 资金 撤出 A股 形成 多米诺 骨牌 效应 ， 而 随着 再 一次 的 暴跌 ， 大量 的 股权 质押 逼近 平仓 线 使得 更多 的 被动性 抛盘 出现 …… 近期 的 这些 事件 集中 出现 ， 使得 当前 市场 变得 更为 复杂 ， 市场 谨慎 心态 空前 ， 弱势 恐 仍 将 延续 。 　　“ 持续性 的 暴跌 之后 ， 成交 量 仍旧 持续 缩量 ， 两 融 余额  下降 ， 体现 出 当前 场外 资金 入市 意愿 寥寥 ， 尽管 今日 盘中 两桶 油 现 护盘 迹象 ， 但 在 目前 投资者 心态 受到 持续 严重 打击 的 背景 下 ， 想 很快 扭转 下跌 之 势 不容易  。 ” 李凯 分析 称 。　　爱建 证券 一位 分析 人士 对 财新记者 表示 ， 从 国内外 情况 来 看 ， 近日 并 无 重大 利空 消息 ， 国内 经济 运行 情况 虽 不乐观 ， 但 短期 内 发生 系统性 风险 可能性 较低 ， 从 基本 面 上 很难 找到 诱发 本轮 暴跌 的 元凶 ， 硬 要 找 原因 的 话 ，流动性 偏紧 的 预期 可能 是 一个 方面 。　　“ 但 更多 的 因素 可能 要 从 市场 角度 来 解读 ， 比如 私募 大规模 触及 清仓线 、 部分 机构 投资 者 触及 止损线 等 这些 可能性 也 不能 排除 。 总的 来说 在 没有 显著 利空 的 情况 下 ， 出现 暴跌 更多 表现 了 场内 投资者 极端 脆弱 的 情绪 。 ” 该 人士 称 。 ";
              //  Document doc = new Document(2, str6);   // 第一个参数document id，第二个参数语种，第三个参数文本字符串
        long s1 = System.currentTimeMillis();
        long t,s;
        ThemeClassifier classifer = new ThemeClassifier();
		int r =  classifer.predict("科技","zh");	//先初始化一次
		r = TextProcessFactory.getSentiAnalyser().analyseParagraph(str1, "zh");
		long t1 = System.currentTimeMillis();
		System.out.println(r + " " + (t1-s1));
		s=System.currentTimeMillis();
		r = classifer.predict(str3,"zh");
		t = System.currentTimeMillis();
		System.out.println(r + " " + (t-s));
		s=t;
		r = classifer.predict(str4,"zh");
		t = System.currentTimeMillis();
		System.out.println(r + " " + (t-s));
		
		
		ExecutorService executorService = Executors.newCachedThreadPool();
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		testClassify t2 = new testClassify();
		for(int i = 0; i < 10; i++){
			executorService.submit(t2);
		}
		
	}


}
