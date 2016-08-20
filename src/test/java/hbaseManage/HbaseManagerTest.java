package hbaseManage;

import org.junit.Test;

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
    public void testGetTableFamilys() throws Exception {
        System.out.println( "Family: ++++++++ "+ HbaseManager.getTableFamilies("NewsArticleBE2"));
    }
}