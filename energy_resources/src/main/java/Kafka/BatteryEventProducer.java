package Kafka;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.generic.IndexedRecord;
import org.apache.commons.io.IOUtils;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.IntegerSerializer;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class BatteryEventProducer<I extends Number, G extends IndexedRecord> {

    public static void main(String[] args) throws IOException {
        String bootstrapServers = "127.0.0.1:9092";
        Properties producerProps = new Properties();
        producerProps.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
            bootstrapServers);
        producerProps.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
            IntegerSerializer.class.getName());
        producerProps.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
            "io.confluent.kafka.serializers.KafkaAvroSerializer");
        producerProps.put(ProducerConfig.ACKS_CONFIG, "1");

        // construct kafka producer.
        Producer<Integer, GenericRecord> producer =
            new KafkaProducer<Integer, GenericRecord>(producerProps);
        // message key.
        int BatteryEventIdInt = 1;
        GenericRecord record = buildRecord();

        // send avro message to the topic page-view-event.
        producer.send(
            new ProducerRecord<Integer, GenericRecord>("battery-event",
                BatteryEventIdInt, record));

        producer.flush();
        // this is alternatively option to send data if this is the end of sending data
        producer.close();
    }


    public static GenericRecord buildRecord() throws IOException {
        //  IOUtils ioUtils = new IOUtils();
        // avro schema avsc file path.
        String schemaPath = "/avro/BatteryEvent.avsc";
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
        record.put("itemId", "any-item-id");

        return record;
    }
}
