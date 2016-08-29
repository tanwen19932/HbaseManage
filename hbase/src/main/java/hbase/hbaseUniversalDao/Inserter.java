package hbase.hbaseUniversalDao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import tw.utils.PropertiesUtil;

public abstract class Inserter {
	
	protected static Log LOG ;
	protected MyDao myDao ;
	
	protected static String PROPATH =null;
	protected static Properties PROPERTIES ;
	
	protected static String	 JDBC_DRIVER ;
	protected static String	 JDBC_URL ;
	
	protected static String	 JDBC_USER	;
	protected static String	 JDBC_PWD	;
	
	
	protected static int PAGESIZE ;
	protected static int STARTROW ;
	protected static int TOTALCOUNT;
	protected static int PAGE = 0;
	protected static String SQL ;
	private static Connection  connection= null;
	
	public Inserter(MyDao myDao) {
		// TODO Auto-generated constructor stub
		this.myDao = myDao;
	}
	
	public Inserter(MyDao myDao , String propath) {
		// TODO Auto-generated constructor stub
		this.myDao = myDao;
		PROPATH = propath;
	}
	
	private void getPro() throws SQLException{
		LOG=LogFactory.getLog( this.getClass() );
//		String rootPath=this.getClass().getResource("/").getPath()+"properties/";
		String rootPath="E:/"+"properties/";
		if(PROPATH ==null ) PROPATH = rootPath + this.getClass().getSimpleName()+".properties";
		else PROPATH = rootPath + this.getClass().getSimpleName()+"_"+PROPATH+".properties";
		PROPERTIES = PropertiesUtil.getProp(PROPATH);
		connection = DriverManager.getConnection( PROPERTIES.getProperty("JDBC_URL"),PROPERTIES.getProperty("JDBC_USER"),PROPERTIES.getProperty("JDBC_PWD") );
		PAGESIZE = Integer.parseInt( (String)PROPERTIES.get("PAGESIZE") ); 
		TOTALCOUNT = Integer.parseInt( (String)PROPERTIES.get("TOTALCOUNT") );
		SQL = (String) PROPERTIES.get("SQL");
	}
	public static synchronized void increPage(){
		PAGE++;
	}
	
	public void insert () throws SQLException{
		
		STARTROW = TOTALCOUNT;
		while(true){
			StringBuffer sb = new StringBuffer();
			sb.append(SQL).append(" limit ").append( PAGESIZE*PAGE+STARTROW ).append(",").append(PAGESIZE);
			System.out.println(sb.toString());
			PreparedStatement preparedStatement = connection.prepareStatement(sb.toString());
			ResultSet rs = preparedStatement.executeQuery();
			int i = 0;
			while( rs.next() ){
				i++;
				try{
					Object obj= myDao.getObjFromR(rs);
					myDao.insert(obj);
					TOTALCOUNT++;
					PROPERTIES.setProperty( "TOTALCOUNT",String.valueOf(TOTALCOUNT) );
					PropertiesUtil.saveProp(PROPERTIES, PROPATH);
					LOG.info("insert success! totalCount = " + TOTALCOUNT);
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			System.gc();
			rs .close();
			rs = null;
			preparedStatement.close();
			preparedStatement = null;
			sb=null;
			PAGE++;
			LOG.info(this.getClass().getSimpleName()  + "  page : "+PAGE+"   DONE! totalSize:" + TOTALCOUNT );
			if(i == 0 ) break;
		}
	}
	
	class InsertThread extends Thread{
		@Override
		public void run() {
			super.run();
			while(true){
				try{
					insert();
					Thread.sleep(10*60*1000);
				}catch(Exception e){
					e.printStackTrace();
				}
				
			}
		}
	}
	
	public void start() {
		try {
			getPro();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		InsertThread increThread = new InsertThread();
		increThread.start();
		
//		InsertThread[] insertThread = new InsertThread[threads];
//		for(int i = 0 ;i < threads ; i++){
//			insertThread[i] = new InsertThread();
//			insertThread[i].start();
//		}
		
	}

	public static String getPROPATH() {
		return PROPATH;
	}

	public static void setPROPATH(String pROPATH) {
		PROPATH = pROPATH;
	}
	
//	public void getTotalCount() {
//		TOTALCOUNT = ?;
//	}
}
