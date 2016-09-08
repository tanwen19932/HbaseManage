package hbase.temp;

import news.News;
import news.NewsDao;
import org.apache.hadoop.hbase.filter.CompareFilter.CompareOp;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.util.Bytes;
import org.json.JSONObject;
import tw.utils.FileUtils;
import tw.utils.ReflectUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;

public class Get100wData {
    public static void main(String[] args) {
        NewsDao newsDao = new NewsDao(){
            int count = 0;
            @Override
            public void handleNews(News news) {
                // TODO Auto-generated method stub
                super.handleNews(news);
                //				XStream xStream = new XStream();
                //				xStream.processAnnotations(News.class);
                //				String xml = xStream.toXML(news);
                //				System.err.println(xml);
                if(news.getLanguageTname() == null) return;
                Field[] fileds = news.getClass().getDeclaredFields();
                JSONObject jo = new JSONObject();
                count++ ;
                if(count>5000) System.exit(0);
                String dir = String.valueOf( 5 );
                File dirFile = new File(dir);

                if( !dirFile.exists() ){
                    dirFile.mkdirs();
                }
                File file = new File(dir+"/"+ count);
                if(file.exists()) return;
                for(int i = 0 ; i < fileds.length ; i++ ){
                    jo.put( fileds[i].getName(), ReflectUtil.getObjFValue(news, fileds[i]));
                }
                System.out.println( jo.get("id") + "totalCount "+ count);
                try {
                    FileUtils.writeFile(file, jo.toString(), "utf-8");
                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        };
        Filter filter1 = new SingleColumnValueFilter(
                Bytes.toBytes("I"),
                Bytes.toBytes("languageCode"), CompareOp.EQUAL, Bytes.toBytes("zh"));
        Filter filter2 = new SingleColumnValueFilter(
                Bytes.toBytes("I"),
                Bytes.toBytes("docLength"), CompareOp.GREATER_OR_EQUAL, Bytes.toBytes("9000"));
        //		ColumnPaginationFilter filter = new ColumnPaginationFilter(5, 0);
        //		Filter filter1 = new RandomRowFilter((float) 0.8);
        //		Filter pf = new PrefixFilter(Bytes.toBytes("03")); // OK  筛选匹配行键的前缀成功的行
        FilterList filterList = new FilterList(
                FilterList.Operator.MUST_PASS_ALL);
        filterList.addFilter(filter1);
        filterList.addFilter(filter2);
        //		filterList.addFilter(pf);
        newsDao.handleAll(filterList);
    }
}
