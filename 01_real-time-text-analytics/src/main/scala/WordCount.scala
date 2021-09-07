import org.apache.kafka.streams._
import org.apache.kafka.common.serialization._

import java.util.Properties

object WordCount {
  def main(args: Array[String]): Unit = {
    val config = {
      val properties = new Properties()
      properties.put(StreamsConfig.APPLICATION_ID_CONFIG, "stream-application")
      properties.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:29092")
      properties.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass)
      properties.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass)
      properties
    }

    val builder = new StreamsBuilder()
    val sourceStream = builder.stream("SourceTopic")
    sourceStream.to("SinkTopic")

    val streams = new KafkaStreams(builder.build(), config)
    streams.start()
  }
}