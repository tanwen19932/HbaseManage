package hbase.move;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Move {
	public static void exportT(Collection<String> tables){
		String pattern = "./hbase org.apache.hadoop.hbase.mapreduce.Export TABLE  hdfs://192.168.55.95:54310/hbase/backup/TABLE/";
		for(String s: tables){
			System.out.println( pattern.replaceAll("TABLE", s) ); 
		}
	}
	public static void importT(Collection<String> tables){
//		String pattern = "./hbase org.apache.hadoop.hbase.mapreduce.Import TABLE  hdfs://192.168.55.95:54310/hbase/backup/TABLE/";
		String pattern = "hbase org.apache.hadoop.hbase.mapreduce.Import TABLE  /hbase/backup/backup/TABLE/";
//		String pattern = "./hbase org.apache.hadoop.hbase.mapreduce.Import TABLE  /home/zyyt/backup/TABLE/";
		for(String s: tables){
			System.out.println( pattern.replaceAll("TABLE", s) ); 
		}
	}
	
	public static void createT(Collection<String> tables){
		String pattern = "create \"TABLE\" ,\"FAMILY\"";
		for(String s: tables){
			System.out.println( pattern.replaceAll("TABLE", s).replaceAll("FAMILY", s.substring(0,1)) ); 
		}
	}
	
	
	public static void main(String[] args) {
		Set<String> tables = new HashSet<>();
		String temp = "Product,CompanyList,I2C,CompanyInfoTest,CompRankInfoTest,Special,Warn; NewsAnalyse,Product,ProductInfoV2,ProRankInfoV2 SocialSpecial  Special Company, CompanyInfo, CompRankInfo;CompanyInfoV2, CompRankInfoV2;Company, CompanyInfo, CompRankInfo;CompanyInfoV2, CompRankInfoV2;Warn; NewsAnalyse";
		for (String s :temp.replaceAll("[\\s*|;]", ",").split(",")){
			s = s.replaceAll(" ", "");
			if(!s.equals(""))
			tables.add(s);
		};
		importT(tables);
	}
	
}
