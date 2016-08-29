package news.tempNews.hbase;

import java.io.IOException;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.json.JSONObject;

import tw.utils.ExcelUtil;
import tw.utils.HttpUtil;

public class ExcelPOI {
	public static void main(String[] args) throws IOException {
		Workbook book = ExcelUtil.getHExcelWorkbook("E:/insert/one belt one road.xls");
		Sheet sheet = book.getSheetAt(1);
		for (int i = 1; i < sheet.getLastRowNum(); i++) {
			Row row = sheet.getRow(i);
			String[] str = new String[10];
			JSONObject jsonObject = new JSONObject();
			for (int j = 0; j < row.getLastCellNum(); j++) {
				str[j]=row.getCell(j).getStringCellValue().trim();
//				System.out.println(str[j]);
			}
			jsonObject.put("titleSrc", str[0]);
			jsonObject.put("pubdate", str[2]);
			jsonObject.put("textSrc", str[1]);
			jsonObject.put("url", str[3]);
			jsonObject.put("comeFrom", "Yangnan");
			jsonObject.put("mediaType", 1);
			jsonObject.put("mediaLevel", 5);
			jsonObject.put("mediaNameSrc", str[4]);
			jsonObject.put("countryNameZh", str[5]);
			jsonObject.put("languageTname", str[6]);
//			System.out.println(jsonObject);
			String output =HttpUtil.doPost("http://43.240.136.130:8089/webservice/insertNewsForPost/dfa95dbe9d657116c5613d6b6c05abcd", 
					jsonObject);
			System.out.println(output);
			
		}	
	}
}
