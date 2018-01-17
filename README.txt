Authors: Rahul Bangar, Jeremy Runyan, Saurabh Shendye, Todd Worcester


To build the project run "mvn install" in the root directory.
The project jar will be under "target". Use the full jar: "cs455-project-1.0-SNAPSHOT-jar-with-dependencies.jar"

Start the spark program by running:
"$SPARK_HOME/bin/spark-submit --class cs455.spark.ParseJSON --deploy-mode cluster tp.jar hdfs:/path/to/reddit/data hdfs:/destination"

This will output subreddit stats data in the form:
<subreddit, averageWordCount, averageUniqueWord, uncommonWord, averageWordLength, averageWordsPerSentence>
eg:
(programming,(47.50369484129777,(0.8791861492422425,(0.2635045881861355,(4.220333122113871,(5.475765,14209))))))

There are also similar python scripts written to analyze hand picked quora responses with the same measures.
This can be found under "quora-ref".