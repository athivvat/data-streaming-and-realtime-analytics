import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.sql.*;
import org.apache.spark.sql.streaming.StreamingQuery;

import java.util.Arrays;

public class TFIDF {
    public static void main(String[] args) throws Exception {
        String bootstrapServers = "localhost:29092";
        String subscribeType = "subscribe";
        String SourceTopic = "plaintext-input";
        String SinkTopic = "word-output";

        SparkSession spark = SparkSession
                .builder()
                .appName("TF-IDF")
                .master("local[1]")
                .getOrCreate();

        spark.sparkContext().setLogLevel("ERROR");

        // Read data stream from Kafka
        Dataset<String> doc = spark.readStream()
                .format("kafka")
                .option("kafka.bootstrap.servers", bootstrapServers)
                .option(subscribeType, SourceTopic)
                .load()
                .selectExpr("CAST(value as STRING)")
                .as(Encoders.STRING());

        // Generate running word count
        Dataset<Row> wordCounts = doc.flatMap(
                (FlatMapFunction<String, String>) x -> Arrays.asList(x.split(" ")).iterator(),
                Encoders.STRING()).groupBy("value").count();

        StreamingQuery query = wordCounts
                .writeStream()
                .outputMode("complete")
                .format("console")
                .start();

        query.awaitTermination();
    }
}
