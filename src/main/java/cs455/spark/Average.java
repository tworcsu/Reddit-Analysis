package cs455.spark;

import org.apache.spark.api.java.JavaPairRDD;
import scala.Tuple2;

import java.io.Serializable;

/**
 * Created by jeremy on 4/25/17.
 */
public abstract class Average implements Serializable {
    private static double normalize(int a, int b) {
        double d = (double)a / (double)b;
        if (Double.isNaN(d) || Double.isInfinite(d))
            return 0.0;
        else
            return d;
    }

    private static String cleanComment(String comment) {
        return comment.toLowerCase().replaceAll("\\\\n", " ").replaceAll("'","");
    }

    // <subreddit, word count, unique word count, uncommon word count, grammar count>
    public static JavaPairRDD<String, Tuple2<Double,Tuple2<Double,Tuple2<Double,Tuple2<Double,Tuple2<Double,Integer>>>>>> averageCount(JavaPairRDD<String, String> posts) {
        // first get the initial word counts for every record
        // <sub_reddit, Tuple<words_counted, times that word count was found>>
        WordCount wc = new WordCount();
        UniqueWords uw = new UniqueWords();
        UncommonWords unw = new UncommonWords();
        CharCount cc = new CharCount();
        SentenceLength sl = new SentenceLength();

        JavaPairRDD<String, Tuple2<Integer,Tuple2<Double,Tuple2<Double,Tuple2<Double,Tuple2<Double,Integer>>>>>> rawCounts = posts.mapToPair(
                r -> {
                    String subr = r._1;
                    String clean = cleanComment(r._2);

                    String[] b = clean.split("\\W+");
                    return new Tuple2<>(subr,
                            new Tuple2<>(wc.count(b),
                                    new Tuple2<>(normalize(uw.count(b), wc.count(b)),
                                            new Tuple2<>(normalize(unw.count(b), wc.count(b)),
                                                    new Tuple2<>(normalize(cc.count(b), wc.count(b)),
                                                            new Tuple2<>(normalize(wc.count(b), sl.count(r._2)),
                                                                    1))))));
                }
        );

        // then reduce by the keys so that all word counts get combined together per subreddit
        JavaPairRDD<String, Tuple2<Integer,Tuple2<Double,Tuple2<Double,Tuple2<Double,Tuple2<Double,Integer>>>>>> wordCount = rawCounts.reduceByKey(
                (x,y) -> new Tuple2<>(x._1 + y._1,
                            new Tuple2<>(x._2._1 + y._2._1,
                                    new Tuple2<>(x._2._2._1 + y._2._2._1,
                                            new Tuple2<>(x._2._2._2._1 + y._2._2._2._1,
                                                    new Tuple2<>(x._2._2._2._2._1 + y._2._2._2._2._1,
                                                            x._2._2._2._2._2 + y._2._2._2._2._2)))))
        );

        // now calculate the average word count per subreddit
        JavaPairRDD<String, Tuple2<Double,Tuple2<Double,Tuple2<Double,Tuple2<Double,Tuple2<Double,Integer>>>>>> averageWordCount = wordCount.mapValues(
                (c) -> new Tuple2<>((double)c._1 / (double)c._2._2._2._2._2,
                            new Tuple2<>(c._2._1 / (double)c._2._2._2._2._2,
                                    new Tuple2(c._2._2._1 / (double)c._2._2._2._2._2,
                                            new Tuple2<>(c._2._2._2._1 / (double)c._2._2._2._2._2,
                                                    new Tuple2<>(c._2._2._2._2._1 / (double)c._2._2._2._2._2,
                                                            c._2._2._2._2._2)))))
        );

        return averageWordCount;
    }

    public abstract int count(String[] words);
}
