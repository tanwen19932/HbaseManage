package product.comment.excel;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.Date;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import product.Comment;
import product.Product;
import tw.utils.DateUtil;
import tw.utils.ExcelUtil;
import tw.utils.ReflectUtil;

public class CommentFromExcel implements Iterable<Comment>{
	Sheet sheet;
	Workbook book;
	int[] columns;
	Comment comment;
	
	public CommentFromExcel(String filePath, int sheetNum , Comment comment ,int... columns) throws IOException {
		Workbook book = ExcelUtil.getXExcelWorkbook(filePath);
		this.sheet = book.getSheetAt(sheetNum);
		this.comment = comment;
		this.columns = columns;
	}
	
	public CommentFromExcel(File filePath, int sheetNum , Comment comment ,int... columns) throws IOException {
		this.book = ExcelUtil.getXExcelWorkbook(filePath);
		this.sheet = book.getSheetAt(sheetNum);
		this.comment = comment;
		this.columns = columns;
	}
	
	public void setColumns(int... columns) {
		this.columns = columns;
	}
	
	public Comment getFromSheet(int i ) throws IOException {
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
		Comment comment = null;
		try{
		comment = new Comment();
		if( columns[0] != -1){
			if(columns.length == 5){
				comment.setReferenceName( row.getCell(columns[4]).getStringCellValue().trim()+" "+row.getCell(columns[0]).getStringCellValue().trim() );
			}
			else comment.setReferenceName( row.getCell(columns[0]).getStringCellValue().trim() );
		}
		
		if( columns[1] != -1){
			String date = row.getCell(columns[1]).getStringCellValue().trim();
			if(date.length()<=5) {
				date = "2016-"+date.replace('.', '-');
			}
			comment.setCreatedDate( DateUtil.tryParse(date) );
		}
		if( columns[2] != -1)
			comment.setContentSrc( row.getCell(columns[2]).getStringCellValue().trim() );
		if( columns[3] != -1)
			comment.setCompanyName(row.getCell(columns[3]).getStringCellValue().trim());
		
		}catch (Exception e){
			System.err.println(e);
		}
		
//		System.out.println(str.toString());
		return comment;
		
	}

	@Override
	public Iterator<Comment> iterator() {
		// TODO Auto-generated method stub
		return new Iterator<Comment>() {
			int cursor = 1;
			@Override
			public boolean hasNext() {
				if( cursor > sheet.getLastRowNum())
					return false;
				return true;
			}

			@Override
			public Comment next() {
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
