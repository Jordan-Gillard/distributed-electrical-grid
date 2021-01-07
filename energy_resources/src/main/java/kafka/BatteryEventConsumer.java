package kafka;

import database.Database;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.jdbi.v3.core.Jdbi;
import scala.Int;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;
import java.util.logging.Logger;

public class BatteryEventConsumer {
    public static void main(String[] args) {

        Logger logger = Logger.getLogger("battery event consumer");
        Jdbi jdbi = Database.getJdbiConnectorWithDataSource();
        Database.createEmptyBatteryEventTable(jdbi);
        String bootstrapServers = "127.0.0.1:9092";
        String groupId = "my-fourth-application";
        String topic = "battery_event";

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
        properties.put("schema.registry.url", "http://0.0.0.0:8081");
        //create consumer
        KafkaConsumer<Integer, GenericRecord> consumer = new KafkaConsumer<Integer, GenericRecord>(properties);

        // subscribe consumer to our topic(s)
        consumer.subscribe(Collections.singleton(topic));
        // subscribe several topics we can use Array.asList();

        //poll for new data
        while(true){
            ConsumerRecords<Integer, GenericRecord> records =
                consumer.poll(Duration.ofMillis(100));
            for(ConsumerRecord record : records){
                logger.info("Key: " + record.key() + ", Value: " + record.value());
                logger.info("Partition:" + record. partition() + ", Offset:" + record.offset());
                jdbi.useHandle(handle ->
                 handle.createUpdate("insert into batteryEvent ( charging_source,processor4_temp,device_id,processor2_temp,processor1_temp,charging,current_capacity,inverter_state,moduleL_temp,moduleR_temp,processor3_temp,SoC_regulator,time) " +
                     "values (:charging_source,:processor4_temp,:device_id,:processor2_temp,:processor1_temp,:charging,:current_capacity,:inverter_state,:moduleL_temp,:moduleR_temp,:processor3_temp,:SoC_regulator,:time)").
                     bindBean(record.value())
                );

            }
        }

    }
}
