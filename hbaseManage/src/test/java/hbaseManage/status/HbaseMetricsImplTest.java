package hbaseManage.status;

import org.junit.Test;

/**
 * @author TW
 * @date TW on 2016/8/25.
 */
public class HbaseMetricsImplTest {

    @Test
    public void testGetStr()
            throws Exception {
        System.out.println( new HbaseMetricsImpl().getStr("{\n" +
                "    \"ip\":\"192.168.59.12:60010\",\n" +
                "    \"qry\":\"Server\",\n" +
                "    \"name\":\"Master\"\n" +
                "}\n"));
    }

}