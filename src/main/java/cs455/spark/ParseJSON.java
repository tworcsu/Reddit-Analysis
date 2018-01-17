package cs455.spark;

import com.google.gson.Gson;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.storage.StorageLevel;
import scala.Tuple2;

/**
 * Created by toddw on 4/23/17.
 */
public class ParseJSON {

    public static void main(String[] args) {
        JavaSparkContext sc = new JavaSparkContext();
        JavaRDD<String> data = sc.textFile(args[0]);
        JavaRDD<Record> records = data.map((line) -> new Gson().fromJson(line, Record.class));

        // parse every record of the data to get <subreddit, body>
        JavaPairRDD<String, String> allPosts = records.mapToPair((r) -> new Tuple2<>(r.getSubreddit(),r.getComment()));

        // count number of reddit posts
        JavaPairRDD<String, Integer> counts = records.mapToPair(w -> new Tuple2<>(w.getSubreddit(), 1));
        JavaPairRDD<String, Integer> commentCount = counts.reduceByKey((x, y) -> x + y);
        // commentCount.saveAsTextFile(args[1] + "/commentCount");

        final int COUNT = 10000;
        JavaPairRDD<String, Integer> bigCounts = commentCount.filter((c) -> c._2 >= COUNT);
        bigCounts.persist(StorageLevel.MEMORY_AND_DISK());
        // bigCounts.saveAsTextFile(args[1] + "/bigCounts");

        JavaPairRDD<String, String> posts = bigCounts.join(allPosts).mapToPair(
                (e) -> new Tuple2<>(e._1, e._2._2)
        );
        // posts.saveAsTextFile(args[1] + "/posts");

        // average reddit word counts
//        JavaPairRDD<String, Double> averageWordCounts = Average.averageCount(posts,new WordCount());
//        averageWordCounts.saveAsTextFile(args[1] + "/wordCount");
//
//        // average reddit unique word counts
//        JavaPairRDD<String, Double> averageUniqueWordCounts = Average.averageCount(posts,new UniqueWords());
//        averageUniqueWordCounts.saveAsTextFile(args[1] + "/uniqueWordCount");
//
//        // average reddit uncommon words used counts
//        JavaPairRDD<String, Double> averageUncommonCounts = Average.averageCount(posts,new UncommonWords());
//        averageUncommonCounts.saveAsTextFile(args[1] + "/uncommonWordCount");
//
//        // average reddit grammar errors in posts
//        JavaPairRDD<String, Double> averageGrammarCounts = Average.averageCount(posts,new GrammarCheck());
//        averageGrammarCounts.saveAsTextFile(args[1] + "/grammarCount");

        JavaPairRDD<String, Tuple2<Double,Tuple2<Double,Tuple2<Double,Tuple2<Double,Tuple2<Double,Integer>>>>>> results =
                Average.averageCount(posts);
        results.saveAsTextFile(args[1]);

        sc.close();
    }
}
