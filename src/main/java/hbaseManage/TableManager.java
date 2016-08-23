package hbaseManage;

import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.client.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static tw.utils.HbaseUtil.*;
import static tw.utils.StringUtil.isEmpty;

/**
 * @author TW
 * @date TW on 2016/8/22.
 */
public class TableManager {
    String tableName;
    HTable htable = null;

    public TableManager(String tableName) {
        this.tableName = tableName;
        connect();
    }

    public void connect() {
        try {
            htable = new HTable(getConf(), sToB(tableName));
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public List getValues(String family, int page, String startRow, String endRow, List filters) {
        List<Map<String, String>> result = new ArrayList<>();
        try {
            Scan scan = new Scan();
            if (!isEmpty(startRow))
                scan.setStartRow(sToB(startRow));
            if (!isEmpty(endRow))
                scan.setStopRow(sToB(endRow));
            ResultScanner resultScanner = htable.getScanner(scan);
            for (Result r : resultScanner) {
                if (r.listCells() != null) {
                    Map<String, String> map = new HashMap<>();
                    String rowkey = " ";
                    for (Cell cell : r.listCells()) {
                        rowkey = bToS(cell.getRow());
                        String qualifier = (bToS(cell.getQualifier())).trim();
                        // System.out.println(qualifier+" "+Bytes.toString(cell.getValue()));
                        map.put(qualifier, bToS(cell.getValue()));
                    }
                    map.put(HbaseConstant.ROWKEY, rowkey);
                    result.add(map);
                }
            }
        } catch (Exception e) {
        }
        return result;
    }

    public boolean add(String family, Map<String, String> values) {
        Put put = new Put(sToB(values.get(HbaseConstant.ROWKEY)));
        values.remove(HbaseConstant.ROWKEY);
        for (Map.Entry entry : values.entrySet()) {
            put.addColumn(sToB(family), sToB(entry.getKey().toString()), sToB(entry.getValue().toString()));
        }
        try {
            htable.put(put);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(String family, Map<String, String> values) {
        return add(family, values);
    }

    public boolean delete(String rowkey) {
        Delete delete = new Delete(sToB(rowkey));
        try {
            htable.delete(delete);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void close() {
        if (htable != null) {
            try {
                htable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
