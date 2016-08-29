                                                                                                                                                                                                                                                                                                                                                                                                  package product.comment.excel;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import tw.utils.ExcelUtil;

public class StringFromExcel implements Iterable<String[]>{
	Sheet sheet;
	Workbook book;
	int[] columns;
	
	public StringFromExcel(String filePath, int sheetNum ) throws IOException {
		try{
			book = ExcelUtil.getHExcelWorkbook(filePath);
		}catch(Exception e){
			book = ExcelUtil.getXExcelWorkbook(filePath);
		}
		this.sheet = book.getSheetAt(sheetNum);
	}
	
	public StringFromExcel(File filePath, int sheetNum ) throws IOException {
		this.book = ExcelUtil.getHExcelWorkbook(filePath);
		this.sheet = book.getSheetAt(sheetNum);
	}
	
	public void setColumns(int... columns) {
		this.columns = columns;
	}
	
	public String[] getString(int... columns) throws Exception{
		if( columns.length==1 ){
			return null;
		}
		String[] values = new String[ columns.length - 1];
		int rowNum = columns[0];
		Row row = sheet.getRow(rowNum);
		for (int i = 1; i <= values.length; i++) {
			Cell cell = row.getCell(columns[i]);
			values[i-1] = cell.getStringCellValue().trim() ;
		}
		// 列数
//		Product product = new Product();
//		product.setProductName(str[0]);
//		product.setCompanyName(str[1]);
//		product.setIndustryName("手机");
		
//		Company company = new Company();
//		company.setCompanyName(str[1]);
//		company.setCompanyAlias(str[2]);
//		company.setIndustryName("手机");
//		
		
//		System.out.println(str.toString());
	
		
		return values;
	}
	
	

	@Override
	public Iterator<String[]> iterator() {
		// TODO Auto-generated method stub
		return new Iterator<String[]>() {
			int cursor = 1;
			@Override
			public boolean hasNext() {
				if( cursor > sheet.getLastRowNum())
					return false;
				return true;
			}

			@Override
			public String[] next() {
				cursor++ ;
				try {
					int[] columns  = new int[sheet.getRow(cursor-1).getLastCellNum()+1];
					columns[0] = cursor-1;
					for (int i = 0; i < sheet.getRow(cursor-1).getLastCellNum(); i++) {
						columns[i+1] = i;
					}
					
					return getString(columns);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
			}
		};
	}
	public void close() throws IOException {
		if( book != null ){
			book.close();
		}
	}
	
}
