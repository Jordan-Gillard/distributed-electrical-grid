package kafka;

import avro.ChargingEvent;
import database.Database;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.jdbi.v3.core.Jdbi;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;
import java.util.logging.Logger;

public class DatabaseConsumer {
    public static void main(String[] args) {

        Logger logger = Logger.getLogger("battery event consumer");
        Jdbi jdbi = Database.getJdbiConnectorWithDataSource();
        Database.createEmptyBatteryEventTable(jdbi);
        String bootstrapServers = "127.0.0.1:9092";
        String groupId = "my-fourth-application";
        String topic = "charging_event";

        //create consumer configs
        Properties properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
            bootstrapServers);
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
            IntegerDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
            "io.confluent.kafka.serializers.KafkaAvroDeserializer");
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        properties
            .setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        properties.put("specific.avro.reader", "true");
        properties.put("schema.registry.url", "http://0.0.0.0:8081");
        //create consumer
        KafkaConsumer<Integer, ChargingEvent> consumer =
            new KafkaConsumer<>(properties);

        // subscribe consumer to our topic(s)
        consumer.subscribe(Collections.singleton(topic));
        // subscribe several topics we can use Array.asList();

        //poll for new data
        try {
            while (true) {
                ConsumerRecords<Integer, ChargingEvent> records =
                    consumer.poll(Duration.ofMillis(10000));
                for (ConsumerRecord<Integer, ChargingEvent> record : records) {
                    logger.info(
                        "Key: " + record.key() + ", Value: " + record.value());
                    logger.info(
                        "Partition:" + record.partition() + ", Offset:" + record
                            .offset());
                    jdbi.useHandle(handle -> {
                        handle.execute(
                            "insert into batteryEvent (device_id,charging) values (?, ?)",
                            record.value().getDeviceId(),
                            record.value().getCharging());
                    });

                }
            }
        }
        finally {

            consumer.close();
        }

    }
}
