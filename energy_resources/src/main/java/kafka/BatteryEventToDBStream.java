package kafka;

import io.confluent.kafka.streams.serdes.avro.GenericAvroSerde;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;

import java.util.Collections;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Logger;

public class BatteryEventToDBStream {

    public static void main(String[] args) throws InterruptedException {
        Logger logger = Logger.getLogger("Battery Event Kafka Stream");
        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "battery-event");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG,
            GenericAvroSerde.class);
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG,
            GenericAvroSerde.class);
        props.put("schema.registry.url", "http://0.0.0.0:8081");

        final Map<String, String> serdeConfig = Collections
            .singletonMap("schema.registry.url", "http://0.0.0.0:8081");
        final Serde<GenericRecord> keyGenericAvroSerde = new GenericAvroSerde();
        keyGenericAvroSerde
            .configure(serdeConfig, true); // `true` for record keys
        final Serde<GenericRecord> valueGenericAvroSerde =
            new GenericAvroSerde();
        valueGenericAvroSerde
            .configure(serdeConfig, false); // `false` for record values

        StreamsBuilder builder = new StreamsBuilder();
        KStream<GenericRecord, GenericRecord> textLines = builder
            .stream("battery-event",
                Consumed.with(keyGenericAvroSerde, valueGenericAvroSerde));

        StreamsConfig streamsConfig = new StreamsConfig(props);
        KafkaStreams kafkaStreams =
            new KafkaStreams(builder.build(), streamsConfig);

        kafkaStreams.start();
        Thread.sleep(35000);
        logger.info("Shutting down the battery event stream now");
        kafkaStreams.close();

    }

}
