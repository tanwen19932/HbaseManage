/**
 * @author TW
 * @date TW on 2016/8/31.
 */

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Tuple2;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class SimpleApp {
    public static void main(String[] args)
            throws IOException {
        //String path = new File(".").getCanonicalPath();
        //File workaround = new File(".");
        //System.getProperties().put("hadoop.home.dir", path);
        //new File("./bin").mkdirs();
        //new File("./bin/winutils.exe").createNewFile();

        String logFile = "/README.txt" +
                ""; // Should be some file on your system
        String jarPath = "/Users/TW/jars/SimpleAPP.jar";
        String [] jars = new String[1] ;
        jars[0]= jarPath;
        SparkConf conf = new SparkConf()
                .setJars(jars)
                .setMaster("spark://tanwendeMacBook-Pro.local:7077")
                .setAppName("TW JAVA TEST");
        JavaSparkContext sc = new JavaSparkContext(conf);
        JavaRDD<String> logData = sc.textFile(logFile).cache()
                .flatMap( (s)-> Arrays.asList( s.split(" ")
                ).iterator());
        JavaPairRDD<String, Integer> counts =  logData.mapToPair((s)->new Tuple2(s.toString(), 1)).reduceByKey(( i, i2)->(Integer)i+(Integer)i2);
        List<Tuple2<String, Integer>> output = counts.collect();
        for (Tuple2<?,?> tuple : output) {
            System.out.println(tuple._1() + ": " + tuple._2());
        }
        JavaRDD<Integer>lengths =  logData.map((s)->1);
        long numAs = logData.filter( (s) -> s.contains("a") ).count();
        long numBs = logData.filter( (s) -> s.contains("b") ).count();
        System.out.println("+++++++++++++++++Lines with a: " + numAs + ", lines with b: " + numBs);
    }
}