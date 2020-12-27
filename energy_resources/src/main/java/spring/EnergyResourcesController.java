package spring;

import avro.BatteryEvent;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;
import org.apache.commons.io.IOUtils;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Properties;

@RestController
public class EnergyResourcesController {
    private final Date date = new Date();


    public static GenericRecord buildRecord() throws IOException {
        // TODO: Test this function
        //  IOUtils ioUtils = new IOUtils();
        // avro schema avsc file path.
        String schemaPath = "src/main/avro/BatteryEvent.avsc";
        // avsc json string.
        String schemaString = null;

        FileInputStream inputStream = new FileInputStream(schemaPath);
        try {
            schemaString =
                IOUtils.toString(inputStream, StandardCharsets.UTF_8);
        }
        finally {
            inputStream.close();
        }
        // avro schema.
        Schema schema = new Schema.Parser().parse(schemaString);
        // generic record for page-view-event.
        GenericData.Record record = new GenericData.Record(schema);
        // put the elements according to the avro schema.
        // TODO: What is the item ID supposed to add? Delete if not needed.
//        record.put("itemId", "any-item-id");

        return record;
    }


    @PostMapping("/event/{uuid}")
    public void handlePostEvent(
        @PathVariable("uuid") String uuid,
        BatteryEvent batteryEvent) throws IOException {
        System.out.printf("Event Received: %s", batteryEvent);
        batteryEvent.setTime(date.getTime());

        // Create producer properties
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
            "127.0.0.1:9092");
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
            StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
            StringSerializer.class.getName());

        /**
         // Create the producer
         // key is a String, the value is a String
         KafkaProducer<String, GenericRecord> producer =
         new KafkaProducer<>(properties);

         // create a producer record
         ProducerRecord<String, GenericRecord>
         record = new ProducerRecord<>("battery_event", batteryEvent);
         // Send data - asynchronous
         producer.send(record);
         */
        Producer<Integer, GenericRecord> producer =
            new KafkaProducer<>(properties);
        // message key.
        int BatteryEventIdInt = 1;
        GenericRecord record = buildRecord();

        // send avro message to the topic page-view-event.
        producer.send(
            new ProducerRecord<>("battery-event", BatteryEventIdInt, record));
        // The producer.send method is asynchronous, so the program exits before
        // it is actually sent.
        // This is how we wait for the record to be sent. Flush the producer.
        producer.flush();
        // flush and close producer.
        producer.close();
    }
}
