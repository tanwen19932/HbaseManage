///**
// * @author TW
// * @date TW on 2016/8/31.
// */
//
//import org.apache.spark.SparkConf;
//import org.apache.spark.api.java.JavaPairRDD;
//import org.apache.spark.api.java.JavaRDD;
//import org.apache.spark.api.java.JavaSparkContext;
//import scala.Tuple2;
//
//import java.io.File;
//import java.io.IOException;
//import java.util.Arrays;
//import java.util.List;
//
//public class SimpleApp {
//    public static void main(String[] args)
//            throws IOException {
//        String path = new File(".").getCanonicalPath();
//        //File workaround = new File(".");
//        System.getProperties().put("hadoop.home.dir", path);
//        new File("./bin").mkdirs();
//        new File("./bin/winutils.exe").createNewFile();
//        String logFile = "D:/cisionMediaSrc"; // Should be some file on your system
//        SparkConf conf = new SparkConf().setAppName("Simple Application");
//        JavaSparkContext sc = new JavaSparkContext("local", "Simple Application");
//        JavaRDD<String> logData = sc.textFile(logFile).cache().flatMap((s)-> Arrays.asList( s.split("\\s|,|\\|")).iterator());
//        JavaPairRDD<String, Integer> counts =  logData.mapToPair((s)->new Tuple2(s, 1)).reduceByKey(( i, i2)->(Integer)i+(Integer)i2);
//
//        List<Tuple2<String, Integer>> output = counts.collect();
//        for (Tuple2<?,?> tuple : output) {
//            System.out.println(tuple._1() + ": " + tuple._2());
//        }
//
//
//
//        JavaRDD<Integer>lengths =  logData.map((s)->1);
//        System.out.println(lengths.reduce((a,b)->a+b));
//
//        long numAs = logData.filter( (s) -> s.contains("a") ).count();
//
//        long numBs = logData.filter( (s) -> s.contains("b") ).count();
//
//        System.out.println("+++++++++++++++++Lines with a: " + numAs + ", lines with b: " + numBs);
//    }
//}