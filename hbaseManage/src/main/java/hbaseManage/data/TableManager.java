package hbaseManage.data;

import com.google.common.collect.Sets;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.client.*;

import java.io.IOException;
import java.util.*;

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

    public List getValues(String[] families, int page, int pageSize, String startRow, String endRow, List<HbaseFilter> filters) {
        List<Map<String, String>> result = new ArrayList<>();
        Set<String> familiesSet = Sets.newHashSet(families);
        try {
            Scan scan = new Scan();
            if (!isEmpty(startRow))
                scan.setStartRow(sToB(startRow));
            if (!isEmpty(endRow))
                scan.setStopRow(sToB(endRow));


            scan.setFilter(HbaseFilterFactory.getFilterList(filters));
            ResultScanner resultScanner = htable.getScanner(scan);
            int temp = 0;
            for (Result r : resultScanner) {
                if (temp < (page - 1) * pageSize) {
                    temp++;
                    continue;
                }
                if (r.listCells() != null) {
                    Map<String, String> map = new HashMap<>();
                    String rowkey = " ";
                    for (Cell cell : r.listCells()) {
                        String family = bToS(cell.getFamily());
                        rowkey = bToS(cell.getRow());
                        if (!familiesSet.contains(family)) {
                            continue;
                        }
                        String qualifier = (bToS(cell.getQualifier())).trim();
                        // System.out.println(qualifier+" "+Bytes.toString(cell.getValue()));
                        map.put(qualifier, bToS(cell.getValue()));
                    }
                    map.put(HbaseConstant.ROWKEY, rowkey);
                    result.add(map);
                    if (result.size() == pageSize) {
                        break;
                    }
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

    //@Override
    //protected void finalize()
    //        throws Throwable {
    //    close();
    //    super.finalize();
    //}

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
