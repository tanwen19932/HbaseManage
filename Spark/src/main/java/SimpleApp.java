/**
 * @author TW
 * @date TW on 2016/8/31.
 */

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

public class SimpleApp {
    public static void main(String[] args) {
        String logFile = "D:/cisionMediaSrc"; // Should be some file on your system
        SparkConf conf = new SparkConf().setAppName("Simple Application");
        JavaSparkContext sc = new JavaSparkContext("local", "Simple Application");
        JavaRDD<String> logData = sc.textFile(logFile).cache();

        long numAs = logData.filter( (s) -> s.contains("a") ).count();

        long numBs = logData.filter( (s) -> s.contains("b") ).count();

        System.out.println("Lines with a: " + numAs + ", lines with b: " + numBs);
    }
}