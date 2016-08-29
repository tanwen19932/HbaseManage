package product;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;

import hbase.hbaseUniversalDao.MyDao;
import hbase.util.HbaseUtil;


@SuppressWarnings("deprecation")
public class BookDao extends MyDao{
	static HTable table = null;
	static {
		try {
			table = new HTable(HbaseUtil.getConf(), "ProductBook");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void addPut(Put put, String family , String quaifier , String value) {
		if(value != null){
			put.add(Bytes.toBytes(family),Bytes.toBytes(quaifier),Bytes.toBytes(value));
		}
	}
	public void addPut(Put put, String family , String quaifier , int value) {
		put.add(Bytes.toBytes(family),Bytes.toBytes(quaifier),Bytes.toBytes(value));
	}
	public void addPut(Put put, String family , String quaifier , long value) {
		put.add(Bytes.toBytes(family),Bytes.toBytes(quaifier),Bytes.toBytes(value));
	}
	public void addPut(Put put, String family , String quaifier , boolean value) {
		put.add(Bytes.toBytes(family),Bytes.toBytes(quaifier),Bytes.toBytes(value));
	}
	
	
	
	public Book getBookFromR(Result r) {
		Book book= new Book( r.getRow(),
							r.getValue(Bytes.toBytes("I"), Bytes.toBytes("productName")),
							r.getValue(Bytes.toBytes("I"), Bytes.toBytes("author")) ,
							r.getValue(Bytes.toBytes("I"), Bytes.toBytes("pubdate")),
							r.getValue(Bytes.toBytes("I"), Bytes.toBytes("press")) ,
							r.getValue(Bytes.toBytes("I"), Bytes.toBytes("nameSrc")) ,
							r.getValue(Bytes.toBytes("I"), Bytes.toBytes("serials")) ,
							r.getValue(Bytes.toBytes("I"), Bytes.toBytes("language")) ,
							r.getValue(Bytes.toBytes("I"), Bytes.toBytes("format")),
							r.getValue(Bytes.toBytes("I"), Bytes.toBytes("brand")) ,
							r.getValue(Bytes.toBytes("I"), Bytes.toBytes("isbn")), 
							r.getValue(Bytes.toBytes("I"), Bytes.toBytes("size")),
							r.getValue(Bytes.toBytes("I"), Bytes.toBytes("weight")),
							r.getValue(Bytes.toBytes("I"), Bytes.toBytes("imageUrl")),
							r.getValue(Bytes.toBytes("I"), Bytes.toBytes("sort") ));
//		System.out.println(news.getId() + " " + news.getMediaNameZh() + " " + news.getTitleSrc() + " " + news.getCreated() + news.getTextSrc());
		return book;
	}
	
	public Book getBookFromR(ResultSet r) {
		Book book = null;
		try {
			book = new Book( r.getString(1),
								r.getString(2),
								r.getString(3),
								r.getString(4),
								r.getString(5),
								r.getString(6),
								r.getString(7),
								r.getString(8),
								r.getString(9),
								r.getString(10),
								r.getString(11),
								r.getString(12),
								r.getString(13),
								r.getString(14),
								r.getString(15),
								r.getString(16)
								);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		System.out.println(news.getId() + " " + news.getMediaNameZh() + " " + news.getTitleSrc() + " " + news.getCreated() + news.getTextSrc());

		return book;
	}

	public void  create() {
		try {
			String tablename = "ProductBook";
			String[] familys = { "I" };
			HbaseUtil.createTable(tablename, familys);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] args) {
		BookDao bookDao = new BookDao();
		bookDao.create();
		
	}
	@Override
	public Object getObjFromR(ResultSet r) {
		// TODO Auto-generated method stub
		return getBookFromR(r);
	}
	@Override
	public <T> void insert(T obj) {
		// TODO Auto-generated method stub
		Book book = (Book) obj;
		try {
			byte[] rowkey;
			rowkey = Bytes.toBytes( book.getProductId() );
			//澧炲姞涓�鏉¤褰�
			Put put = new Put(rowkey);
			
			addPut(put,"I","productId",
					book.getProductId() );
			addPut(put,"I","productName",
					 book.getProductName());
			
			addPut(put,"I","author",
					 book.getAuthor());
			addPut(put,"I","pubdate",
					 book.getPubdate() );
			addPut(put,"I","press",
					 book.getPress());
			
			addPut(put,"I","nameSrc",
					 book.getNameSrc());
			addPut(put,"I","serials",
					 book.getSerials());
			addPut(put,"I","language",
					 book.getLanguage() );
			addPut(put,"I","format",
					 book.getFormat() ); 
			addPut(put,"I","brand",
					 book.getBrand());
			
			addPut(put,"I","isbn",
					 book.getIsbn());
			addPut(put,"I","size",
					 book.getSize());
			addPut(put,"I","weight",
					 book.getWeight());
			addPut(put,"I","imageUrl",
					 book.getImageUrl());
			addPut(put,"I","sort",
					 book.getSort());
			
			table.put(put);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
