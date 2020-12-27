package kafka;

import avro.BatteryEvent;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class BatteryEventProducer {

    public static void produce(BatteryEvent batteryEvent){

        final String bootstrapServers = "127.0.0.1:9092";

        // Create producer properties
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
            bootstrapServers);
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
            StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
            StringSerializer.class.getName());

        // create the producer
        KafkaProducer<String, String> producer =
            new KafkaProducer<String, String>(properties);

        //create producer record
        ProducerRecord<String, String> record =
            new ProducerRecord<String, String>("battery_event", "hello Jordan");

        //send data - because it is asynchronous, so we need to flush/close data
        producer.send(record);

        producer.flush();
        // this is alternatively option to send data if this is the end of sending data
        producer.close();

    }

}


