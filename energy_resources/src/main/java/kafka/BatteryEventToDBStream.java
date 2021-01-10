package kafka;

import avro.BatteryEvent;

import avro.ChargingEvent;
import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerde;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.specific.SpecificData;
import org.apache.avro.specific.SpecificRecord;
import org.apache.commons.io.IOUtils;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BatteryEventToDBStream {

    public static void main(String[] args) throws InterruptedException {
        Logger logger = Logger.getLogger("Battery Event Kafka Stream");
        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "battery_event");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG,
            Serdes.Integer().getClass().getName());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG,
            SpecificAvroSerde.class);
        props.put("schema.registry.url", "http://0.0.0.0:8081");

        final Map<String, String> serdeConfig = Collections
            .singletonMap("schema.registry.url", "http://0.0.0.0:8081");
        final Serde<Integer> keyIntegerSerde = Serdes.Integer();
        keyIntegerSerde
            .configure(serdeConfig, true); // `true` for record keys

        final Serde<BatteryEvent> batteryEventSerde =
            new SpecificAvroSerde<>();
        batteryEventSerde.configure(serdeConfig, false);

        final Serde<ChargingEvent> chargingEventSerde =
            new SpecificAvroSerde<>();
        chargingEventSerde.configure(serdeConfig, false);

        StreamsBuilder builder = new StreamsBuilder();
        KStream<Integer, BatteryEvent> batteryEventStream = builder
            .stream("battery_event",
                Consumed.with(keyIntegerSerde, batteryEventSerde));
        KStream<Integer, ChargingEvent> chargingStream = batteryEventStream.mapValues(batteryEvent-> new ChargingEvent(batteryEvent.getDeviceId(),batteryEvent.getCharging()));

        chargingStream.to("charging_event", Produced.with(keyIntegerSerde,chargingEventSerde));
        StreamsConfig streamsConfig = new StreamsConfig(props);
        KafkaStreams kafkaStreams =
            new KafkaStreams(builder.build(), streamsConfig);

        kafkaStreams.start();
        Thread.sleep(35000);
        logger.info("Shutting down the battery event stream now");
        kafkaStreams.close();

    }

}
