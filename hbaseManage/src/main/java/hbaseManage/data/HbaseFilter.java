package hbaseManage.data;

import org.apache.hadoop.hbase.filter.CompareFilter;
import org.apache.hadoop.hbase.filter.FilterList;

/**
 * @author TW
 * @date TW on 2016/8/24.
 */
 class HbaseFilter{
    FilterList.Operator operator;
    String family;
    String qualifier;
    CompareFilter.CompareOp compareOp;
    String value;

    public CompareFilter.CompareOp getCompareOp() {
        return compareOp;
    }

    public void setCompareOp(CompareFilter.CompareOp compareOp) {
        this.compareOp = compareOp;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public FilterList.Operator getOperator() {
        return operator;
    }

    public void setOperator(FilterList.Operator operator) {
        this.operator = operator;
    }

    public String getQualifier() {
        return qualifier;
    }

    public void setQualifier(String qualifier) {
        this.qualifier = qualifier;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}


