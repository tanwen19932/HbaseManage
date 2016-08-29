package product.comment.excel;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import product.Comment;
import product.Product;
import tw.utils.ExcelUtil;
import tw.utils.ReflectUtil;

public class ObjectFromExcel implements Iterable<Object>{
	Sheet sheet;
	Workbook book;
	int[] columns;
	Object obj;
	
	public ObjectFromExcel(String filePath, int sheetNum , Object obj ,int... columns) throws IOException {
		Workbook book = ExcelUtil.getHExcelWorkbook(filePath);
		this.sheet = book.getSheetAt(sheetNum);
		this.obj = obj;
	}
	
	public ObjectFromExcel(File filePath, int sheetNum , Object obj ,int... columns) throws IOException {
		this.book = ExcelUtil.getHExcelWorkbook(filePath);
		this.sheet = book.getSheetAt(sheetNum);
		this.obj = obj;
	}
	
	public void setColumns(int... columns) {
		this.columns = columns;
	}
	
	public Object getFromSheet(int i ) throws IOException {
		// TODO Auto-generated method stub

		// 创建一个数组 用来存储每一列的值
		Row row = sheet.getRow(i);
		
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
		Object object = ReflectUtil.constructor(this.obj);
		Field[] fields = object.getClass().getDeclaredFields();
		for (int j = 0; j < columns.length; j++) {
			if( columns[j] == -1){
				continue;
			}
			Cell cell = row.getCell(columns[j]);
			cell.getStringCellValue().trim() ;
			ReflectUtil.invokeObjSetMethod(object, fields[j], cell.getStringCellValue().trim() );
		}
		
//		System.out.println(str.toString());
	
		return object;
	}

	@Override
	public Iterator<Object> iterator() {
		// TODO Auto-generated method stub
		return new Iterator<Object>() {
			int cursor = 1;
			@Override
			public boolean hasNext() {
				if( cursor > sheet.getLastRowNum())
					return false;
				return true;
			}

			@Override
			public Object next() {
				cursor++ ;
				try {
					return getFromSheet(cursor-1);
				} catch (IOException e) {
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
