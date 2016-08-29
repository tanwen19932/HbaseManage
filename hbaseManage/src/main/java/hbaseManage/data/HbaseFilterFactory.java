package hbaseManage.data;

import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.util.Bytes;

import java.util.List;

public class HbaseFilterFactory {

    public static FilterList getFilter(HbaseFilter hbaseFilter) {
        FilterList filterList = new FilterList(FilterList.Operator.MUST_PASS_ALL);
        Filter filter1 = new SingleColumnValueFilter(
                Bytes.toBytes(hbaseFilter.getFamily()),
                Bytes.toBytes(hbaseFilter.getQualifier()),
                hbaseFilter.getCompareOp(), Bytes.toBytes(hbaseFilter.getValue()));
        filterList.addFilter(filter1);
        return filterList;
    }

    public static FilterList getFilterList(List<HbaseFilter> hbaseFilters) {
        FilterList filterList = new FilterList(FilterList.Operator.MUST_PASS_ALL);
        if (hbaseFilters != null)
            for (HbaseFilter temp : hbaseFilters) {
                Filter filter = new SingleColumnValueFilter(
                        Bytes.toBytes(temp.getFamily()),
                        Bytes.toBytes(temp.getQualifier()),
                        temp.getCompareOp(), Bytes.toBytes(temp.getValue()));
                filterList.addFilter(filter);
            }
        return filterList;
    }
}
