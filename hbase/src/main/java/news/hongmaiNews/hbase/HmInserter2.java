package news.hongmaiNews.hbase;

import news.DicMap;
import news.News;
import news.NewsDaoRT;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import tw.utils.ObjectPool;
import tw.utils.PropertiesUtil;

import java.io.IOException;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.*;


public class HmInserter2 {

    private static Log LOG = LogFactory.getLog(HmInserter2.class);
    static String rootPath = HmInserter2.class.getResource("/").getPath() + "properties/";

    static String hmcrawlerPath;
    static ObjectPool<Connection> mysqlHMConPool = null;

    private NewsDaoRT newsDao = new NewsDaoRT();
    private static Properties properties;

    static {
        Scanner scanner = new Scanner(System.in);
        System.out.println(" input the properties path: ");
        String line;
        while (true) {
            line = scanner.next();
            if (!line.contains(".")) {
                line = line + ".properties";
            }
            ;
            hmcrawlerPath = rootPath + line;
            try {
                properties = PropertiesUtil.getProp(hmcrawlerPath);
                mysqlHMConPool = new ObjectPool<Connection>(1, 3, 40) {
                    @Override
                    protected boolean valid(Connection object) {
                        try {
                            return object.isValid(3);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        return false;
                    }

                    @Override
                    protected Connection createObject() {
                        properties = PropertiesUtil.getProp(hmcrawlerPath);
                        Connection connection = null;
                        try {
                            Class.forName(properties.getProperty("JDBC_DRIVER"));
                            DriverManager.getConnection(properties.getProperty("JDBC_URL"), properties.getProperty("JDBC_USER"), properties.getProperty("JDBC_PWD"));
                        } catch (Exception e) {
                            e.printStackTrace();}
                        return connection;
                    }
                };
                break;
            } catch (Exception e) {
                System.err.println(" Exception! " + e);
                System.out.println(" input the right path: ");
                continue;
            }
        }

    }

    private static Map<Integer, String> hmLanMap;
    private static Map<Integer, String> hmCountryMap;
    private static Map<Integer, String> hmMediaTMap;

    //	protected static Map<String, Map<String,String> > mediaSrcMap ;
    static {
        hmLanMap = HmDicUtil.getLanMap();
        hmCountryMap = HmDicUtil.getCountryMap();
        hmMediaTMap = HmDicUtil.getMediaTMap();
        //		mediaSrcMap = MediaSrcUtil.getInstance();
    }

    public void start(int thread) {

        List<String> tableList = null;
        while (true) {
            try {
                tableList = PropertiesUtil.getHmDatabases(properties);
                break;
            } catch (IOException e) {
                LOG.error(e);
                e.printStackTrace();
            } catch (ParseException e) {
                LOG.error(e);
                e.printStackTrace();
            }
        }

        IncreThread increThread = new IncreThread();
        increThread.start();

        for (int i = 0; i < thread; i++) {
            InsertThread insertThread = new InsertThread(tableList);
            Thread t = new Thread(insertThread);
            t.start();
        }
        System.out.println(" tasks all clear !");
    }


    private class IncreThread
            extends Thread {
        @SuppressWarnings("deprecation")
        @Override
        public void run() {
            SimpleDateFormat sdf = new SimpleDateFormat("MMdd");
            Date timeBefore = new Date();
            String year = String.valueOf(timeBefore.getYear() + 1900);
            if (!year.equals(properties.get("YEAR"))) {
                return;
            }

            String tableB = "articles_" + sdf.format(timeBefore);
            while (true) {
                try {
                    insertHongmai(tableB);
                    Date timeAfter = new Date();
                    String tableA = "articles_" + sdf.format(timeAfter);
                    if (tableA.equals(tableB)) {
                        tableB = tableA;
                    } else {
                        insertHongmai(tableB);
                        properties.setProperty(tableB, "done-" + properties.getProperty(tableB));
                        PropertiesUtil.saveProp(properties, hmcrawlerPath);
                        tableB = tableA;
                    }
                } catch (Exception e) {
                    LOG.error(e);
                    e.printStackTrace();
                } finally {
                    try {
                        Thread.sleep(1000 * 60 * 5);
                    } catch (Exception e2) {
                    }
                }

            }
        }
    }

    private class InsertThread
            implements Runnable {
        protected List<String> tables = null;

        public InsertThread(List<String> tables) {
            super();
            this.tables = tables;
        }

        @Override
        public void run() {
            while (true) {
                String table = null;
                try {
                    table = tables.remove(0);
                } catch (Exception e) {
                    System.out.println(" tables size :" + tables.size());
                    return;
                }
                if (table != null)
                    System.out.println("Inserting table " + table);
                try {
                    insertHongmai(table);
                } catch (Exception e) {
                    LOG.error("INSERT EXCEPTION!  " + e);
                }
                properties.setProperty(table, "done_" + properties.getProperty(table));
                LOG.info(" table: " + table + "  " + properties.getProperty(table));
                PropertiesUtil.saveProp(properties, hmcrawlerPath);
            }
        }
    }

    public void insertHongmai(String table)
            throws ClassNotFoundException, SQLException, IOException {
        /*
		Map<String, String> siteMap = getDicMap("E:\\1402sitesort\\hongmai\\0.txt");
		siteMap.putAll(getDicMap("E:\\1402sitesort\\hongmai\\1.txt"));
		siteMap.putAll(getDicMap("E:\\1402sitesort\\hongmai\\2.txt"));
		*/

        int pageSize = 10000;
        int page = 0;
        String num = (String) properties.get(table);
        if (num.startsWith("done")) {
            return;
        }
        int startRow = Integer.parseInt(num);
        int totalCount = startRow;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        while (true) {
            String sql = "select * from  " + table + " " + "  limit " +
                    (pageSize * page + startRow) + "," + pageSize;
            System.out.println(sql);
            PreparedStatement preparedStatement = mysqlHMConPool.borrowObject().prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            int i = 0;
            int insertCount = 0;
            while (rs.next()) {
                totalCount++;
                i++;
                try {
                    String id = null; //byte[] id ;
                    int hmMedia = rs.getInt("media_type");
                    String mediaTname = hmMediaTMap.get(hmMedia);// 新闻
                    if (mediaTname == null) mediaTname = "其他";
                    int mediaType = DicMap.getMediaType(mediaTname);//媒体类型id

                    String titleSrc = rs.getString(17);
                    if (titleSrc == null) titleSrc = "";
                    String pubdate = rs.getString(28).substring(0, 19);    //发布时间
                    if (pubdate == null) pubdate = "";
                    String textSrc = rs.getString(27);
                    if (textSrc == null) textSrc = "";
                    if (CharsetUtil.check(titleSrc) || CharsetUtil.check(textSrc)) {
                        continue;
                    }
                    String websiteId = rs.getString(3);    //站点id
                    String mediaNameSrc = rs.getString(5);//数据源名称

                    String mediaNameZh = mediaNameSrc;
                    String mediaNameEn = mediaNameSrc;
                    Integer mediaLevel = 4;
                    String countryNameZh = hmCountryMap.get(rs.getInt("region_id"));
                    if (countryNameZh == null) countryNameZh = "其他";
                    String countryNameEn = DicMap.getCountryEn(countryNameZh);
                    String provinceNameZh = null;
                    String provinceNameEn = null;
                    String districtNameZh = null;
                    String districtNameEn = null;


                    String languageCode = null;//en zh
                    String languageTname = null;


                    languageTname = hmLanMap.get(rs.getInt("language_type"));
                    if (languageTname == null) languageTname = "其他";
                    languageCode = DicMap.getLanguageEn(languageTname);//en zh

                    String url = rs.getString(19);
                    //					Map<String, String > valueMap = mediaSrcMap.get( HtmlUtil.getDomain(url) );
                    //					if(valueMap == null){
                    //						System.err.println("数据源不在 数据源库!!");
                    //					}
                    //					else{
                    //						mediaNameZh = valueMap.get("mediaNameZh");
                    //						mediaNameSrc = valueMap.get("mediaNameSrc");
                    //						mediaNameEn  = valueMap.get("mediaNameEn");
                    //						String levelString =  valueMap.get("mediaLevel") ;
                    //						if( levelString != null)
                    //							mediaLevel = Integer.valueOf( levelString ); //级别
                    //						countryNameZh = valueMap.get("countryNameZh");
                    //						countryNameEn = valueMap.get("countryNameEn");
                    //						districtNameZh = valueMap.get("districtNameZh");
                    //						districtNameEn = valueMap.get("districtNameEn");
                    //					}


                    String author = rs.getString(22);

                    String created = sdf.format(new Date(rs.getLong(30) * 1000L));    //爬取时间

                    String updated = sdf.format(new Date(rs.getLong(31) * 1000L));    //更新时间

                    boolean isOriginal = rs.getBoolean(24);//是否原创

                    int view = rs.getInt(20);

                    int docLength = rs.getInt(26);

                    String transFromM = rs.getString(25);
                    int pv = 0;
                    boolean isHome = true; //是否首页
                    boolean isPicture = false;

                    String comeFrom = "HongMai";
                    String comeFromDb = rs.getString(1) + "@" + table;

                    News news = new News(id, mediaType, mediaTname, titleSrc, pubdate, textSrc, websiteId,
                            mediaNameSrc, mediaNameZh, mediaNameEn, mediaLevel.intValue(), countryNameZh,
                            countryNameEn, provinceNameZh, provinceNameEn, districtNameZh, districtNameEn,
                            languageCode, languageTname, author, created, updated, isOriginal,
                            view, url, docLength, transFromM, pv, isHome, isPicture, comeFrom, comeFromDb, "");

                    newsDao.Insert(news);
                    news = null;
                    System.out.println(table + " " + totalCount + " : " + insertCount + mediaNameZh + " - " + countryNameEn + countryNameZh + languageCode + " " + languageTname + " " + titleSrc);
                    insertCount++;
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    properties.setProperty(table, String.valueOf(totalCount));
                    PropertiesUtil.saveProp(properties, hmcrawlerPath);
                }
            }
            System.gc();
            rs.close();
            rs = null;
            preparedStatement.close();
            preparedStatement = null;
            //			mysqlHMCon.close();
            //			mysqlHMCon = null;
            page++;
            LOG.info(" insertHM " + table + "  page : " + page + "   DONE! totalSize:" + totalCount + " insertCount" + insertCount);
            if (i == 0) break;
        }
    }

    public static void main(String[] args)
            throws Exception {
        HmInserter2 hmInserter = new HmInserter2();
        hmInserter.start(3);

        //		HbaseUtil.deleteTable("NewsArticleBE2");
        //		NewsDao.create();

    }
}
