package test;

import edu.buaa.nlp.es.util.DateUtil;

public class testTrick {
	public static String[] str2Arr(String str){
		if(str==null || "".equals(str)) return new String[]{};
		String[] arr=str.split("[;；]");
		for(int i=0; i<arr.length; i++){
			arr[i]=arr[i].trim();
		}
		return arr;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(DateUtil.unix2Time(1463609444));
		System.out.println(str2Arr("Dow Jones ； 品牌指数 ； 商标注册 ； 重新发球 ； 数据").toString());
	}

}
