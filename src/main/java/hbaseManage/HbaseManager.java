package hbaseManage;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.util.Bytes;
import tw.utils.HbaseUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author TW
 * @date TW on 2016/8/19.
 */
public class HbaseManager {
    static final Configuration configuration = HbaseUtil.getConf();
    static HBaseAdmin hBaseAdmin;

    static {
        while (true) {
            try {
                hBaseAdmin = new HBaseAdmin(configuration);
                break;
            } catch (Exception e) {
                e.printStackTrace();
                try {
                    Thread.sleep(9000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                continue;
            }

        }
    }

    /**
     * @return 所有tableName的string List
     *
     * @throws IOException
     */
    public static List getAllTables() throws IOException {
        List<String> tableList = new ArrayList<>();
        for (TableName table : hBaseAdmin.listTableNames()) {
            tableList.add(table.getNameAsString());
        }
        return tableList;
    }

    /**
     * 得到table的描述信息
     *
     * @param tableName
     *
     * @return
     */
    public static Map getTableDescriber(String tableName) {
        Map describer = new HashMap<>();
        describer.put("", "");
        return describer;
    }

    public static List getTableFamilies(String tableName) throws IOException {
        byte[] name = Bytes.toBytes(tableName);
        List famlilies = new ArrayList<>();
        for (HColumnDescriptor descriptor : hBaseAdmin.getTableDescriptor(name).getColumnFamilies()) {
            famlilies.add(descriptor.getNameAsString());
        }
        return famlilies;
    }

    public static List<Map<String,String>> getValues(String tableName ,String families , int page,String startRow , String endRow , List filters){
        return TablePool.get(tableName).getValues(families,page,startRow,endRow,filters);
    }

}
