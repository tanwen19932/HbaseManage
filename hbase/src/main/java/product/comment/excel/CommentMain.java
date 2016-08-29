package product.comment.excel;

import java.io.IOException;

import product.Comment;

public class CommentMain {
	
	public static void main(String[] args) {
		try {
//			HbaseCommentInserter hbaseInserter = new HbaseCommentInserter("ProductCommentBE", "P", "E:\\insert\\京东-手机", new Comment(),
//					0,5,8,1);
//			hbaseInserter.setIndustryName( "手机" );
//			hbaseInserter.setWebsite( "京东" );
//			hbaseInserter.start();
			/**
			 * 1.setReferenceName //商品名字
			 * 2.setCreatedDate	  //时间
			 * 3.setContentSrc	//  内容
			 * 4.setCompanyName	//  厂商
			 */
			
			
//			HbaseCommentInserter hbaseInserter = new HbaseCommentInserter("ProductCommentBE", "P", "E:\\insert\\京东-图书", new Comment(),
//					1,-1,5,2);
//			
//			hbaseInserter.setIndustryName( "图书" );
//			hbaseInserter.setWebsite( "京东" );
//			hbaseInserter.start();
			//!!
			
			HbaseCommentInserter hbaseInserter2 = new HbaseCommentInserter("ProductCommentBE", "P", "E:\\insert\\手机\\天猫", new Comment(),
					5,13,12,1,4);
			
			hbaseInserter2.setIndustryName( "手机" );
			hbaseInserter2.setWebsite( "天猫" );
			hbaseInserter2.start();
//			
			HbaseCommentInserter hbaseInserter = new HbaseCommentInserter("ProductCommentBE", "P", "E:\\insert\\图书\\当当", new Comment(),
					0,8,7,2);
			hbaseInserter.setIndustryName( "图书" );
			hbaseInserter.setWebsite( "当当" );
			hbaseInserter.start();
			/**
			 * 1.setReferenceName //商品名字
			 * 2.setCreatedDate	  //时间
			 * 3.setContentSrc	//  内容
			 * 4.setCompanyName	//  厂商
			 */
			HbaseCommentInserter hbaseInserter3 = new HbaseCommentInserter("ProductCommentBE", "P", "E:\\insert\\图书\\豆瓣", new Comment(),
			0,9,10,2);
			hbaseInserter3.setIndustryName( "图书" );
			hbaseInserter3.setWebsite( "豆瓣" );
			hbaseInserter3.start();
			
			HbaseCommentInserter hbaseInserter4 = new HbaseCommentInserter("ProductCommentBE", "P", "E:\\insert\\图书\\天猫", new Comment(),
			1,9,8,3);
			hbaseInserter4.setIndustryName( "图书" );
			hbaseInserter4.setWebsite( "天猫" );
			hbaseInserter4.start();
			
			HbaseCommentInserter hbaseInserter5 = new HbaseCommentInserter("ProductCommentBE", "P", "E:\\insert\\家电\\京东", new Comment(),
					2,13,8,3);
					hbaseInserter5.setIndustryName( "家电" );
					hbaseInserter5.setWebsite( "京东" );
					hbaseInserter5.start();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
