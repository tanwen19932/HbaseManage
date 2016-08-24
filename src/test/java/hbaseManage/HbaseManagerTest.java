package hbaseManage;

import org.junit.Test;

import java.util.List;

/**
 * @author TW
 * @date TW on 2016/8/19.
 */
public class HbaseManagerTest {

    @Test
    public void testGetAllTables() throws Exception {
        System.out.println("Tables: ++++++++ "+HbaseManager.getAllTables());
    }

    @Test
    public void testGetTableDescriber() throws Exception {
        System.out.println("TableDescriber: ++++++++ "+ HbaseManager.getTableDescriber("NewsArticleBE2"));
    }

    @Test
    public void testGetTableFamilies()
            throws Exception {
        System.out.println( "Family: ++++++++ "+ HbaseManager.getTableFamilies("NewsArticleBE2"));
    }

    @Test
    public void testGetValues()
            throws Exception {
        String[] a = new String[]{"I"};
        //HbaseUtil.getAllRecordToFile("NewsArticleTest","E:/1.txt","I","textSrc");
        List list = HbaseManager.getValues("NewsArticleTest",a,1,10,"","",null);
        System.out.println("testGetValues: +++++++"+list.size() );
    }
}