package news.cisionNews.hbase;

import tw.utils.PropertiesUtil;

import java.io.File;
import java.util.Properties;


public class CisionZipFileCheck {
    static Properties pro = null;
    static String filePath = CisionZipFileCheck.class.getResource("/").getPath() + "cisionZip.properties";
    static long totalCount = 0;
    static long currentFileCount = 0;
    static long checkTotal = 0;
    static boolean firstCheck = true;

    //	static String filePath = "E:/properties/cision.properties";
    static {
        pro = new Properties();
        pro = PropertiesUtil.getProp(filePath);
        try {
            currentFileCount = Long.parseLong(pro.getProperty("currentFileCount", "0"));
            totalCount = Long.parseLong(pro.getProperty("totalCount"));
        } catch (Exception e) {
            totalCount = 0;
        }
    }

    public static void insertDir(String filePath) {
        insertDir(new File(filePath));
    }

    public static void insertDir(File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                try {
                    String name = files[i].getName();
                    if (files[i].isDirectory()) {
                        //insertDir(files[i]);
                    }
                    if (name.endsWith(".zip")) {
                        if (pro.getProperty(name) == null) {
                            System.out.println(name);
                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public static void main(String[] args)
            throws Exception {
        insertDir("/home/zyyt/cisionData");
        //		insertDir("E:\\insert");
    }
}
