package edu.buaa.nlp.util;

import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;


/**
 * 读取配置文件的工具类
 * @author Vincent
 *
 */
public class FileUtil {
	private static Logger logger =Logger.getLogger(FileUtil.class);
	public static String getRootPath(){
		//		return FileUtil.class.getResource("/").getPath();
		return System.getProperty("user.dir");
	}
	
	public static String getPath(String folder){
		return getRootPath()+File.separatorChar+folder;
	}
	
	public static Properties getPropertyFile(String file){
  		String path=getRootPath()+File.separator+file;
  		Properties prop=new Properties();
  		FileInputStream fis=null;
  		try {
  				fis=new FileInputStream(path);
				prop.load(fis);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}finally{
				if(fis!=null){
					try {
						fis.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
  		return prop;
  	}
	
	public static String getRootPath(String folder) {
  		return getRootPath()+folder;
  	}
	
	public static boolean fileExists(String fileName){
        File file = new File(fileName);    
        if(!file.exists()){    
            return false;    
        }else{    
        	return true;
        }    
	}
	public static String getFileName(String fileName){
		int index = fileName.lastIndexOf('\\');
		if(index>0)
		{
			return fileName.substring(index+1);
		}
		return fileName;
	}
	public static String getFilePath(String fileName){
		int index = fileName.lastIndexOf('\\');
		if(index>0)
		{

			String filePath =  fileName.substring(0,index+1);
			if(filePath.substring(filePath.length()-1).equals("\\")== false)
			{
				filePath += "\\";
			}
			return filePath;
		}
		else if(index < 0)
		{
			index = fileName.lastIndexOf('/');
			if(index>0)
			{

				String filePath =  fileName.substring(0,index+1);
				if(filePath.substring(filePath.length()-1).equals("/")== false)
				{
					filePath += "/";
				}
				return filePath;
			}
		}
		
		return "";
	}
	/*
	 * 获得路径文件夹，但是不要反斜杠
	 */
	public static String getFilePath2(String fileName){
		int index = fileName.lastIndexOf('\\');
		if(index>0)
		{
			return fileName.substring(0,index);
		}
		return "";
	}
	
	
	
	public static String getFileTitle(String fileName){
		fileName = getFileName(fileName);
		
		int index = fileName.lastIndexOf('.');
		if(index>0)
		{
			return fileName.substring(0,index);
		}
		return fileName;
	}
	public static String getFileExt(String fileName){
		fileName = getFileName(fileName);
		
		int index = fileName.lastIndexOf('.');
		if(index>0)
		{
			return fileName.substring(index+1);
		}
		return "";
	}
	
	public static boolean createDir(String dirName){
		try
		{
	        File file = new File(dirName);    
	        if(!file.exists()){    
	        	file.mkdirs();
	        	return true;
	        }else{    
	        	return true;
	        }
		}
		catch(Exception e){
			return false;
		}
	}
	
    public static boolean deleteFile(String fileName){    
    	try
    	{
	        File file = new File(fileName);    
	        if(file.isFile() && file.exists()){    
	            if(file.getAbsoluteFile().delete()==true)
	            {
		            return true;
	            }
	            else
	            {
	                return false; 	
	            }
	        }else{    
	            return false;    
	        }    
    	}
    	catch(Exception e)
    	{
    		logger.error(ExceptionUtil.getExceptionTrace(e));
    		return false;
    	}
    }    
		
	
	public static void main(String[] args) {
		System.out.println(getRootPath());
	}
	
	
}
