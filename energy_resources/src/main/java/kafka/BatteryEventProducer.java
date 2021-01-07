package kafka;

import avro.BatteryEvent;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;
import org.apache.commons.io.IOUtils;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.errors.SerializationException;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BatteryEventProducer {

    public static void produce(BatteryEvent batteryEvent) throws IOException {
        Logger logger = Logger.getLogger("Battery Event Producer");
        final String bootstrapServers = "0.0.0.0:9092";

        // Create producer properties
        Properties properties = new Properties();
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
            "org.apache.kafka.common.serialization.IntegerSerializer");
        properties
            .put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
            io.confluent.kafka.serializers.KafkaAvroSerializer.class);
        properties.put("schema.registry.url", "http://0.0.0.0:8081");
        // create the producer
        Producer producer = new KafkaProducer(properties);

        //create producer record
        GenericRecord avroRecord = buildRecord(batteryEvent);

        ProducerRecord<Integer, GenericRecord> record =
            new ProducerRecord<>("battery_event", 1, avroRecord);
        try {
            producer.send(record);
            logger.info("IT FUCKING WORKED!");
        }
        catch (Exception e) {
            // may need to do something with it
            logger.log(Level.WARNING,e.toString());
        }
        // When you're finished producing records, you can flush the producer to ensure it has all been written to Kafka and
        // then close the producer to free its resources.
        finally {
            producer.flush();
            producer.close();
        }
    }


    public static GenericRecord buildRecord(BatteryEvent batteryEvent)
        throws IOException {
        Logger logger = Logger.getLogger("Battery Event Producer");
        //  IOUtils ioUtils = new IOUtils();
        // avro schema avsc file path.
        String schemaPath =
            "energy_resources/src/main/resources/avro/BatteryEvent.avsc";
        // avsc json string.
        String schemaString = null;

        FileInputStream inputStream = new FileInputStream(schemaPath);
        try {
            schemaString =
                IOUtils.toString(inputStream, StandardCharsets.UTF_8);
        }
        catch (Exception e){
            logger.log(Level.WARNING,e.toString());
        }
        finally {
            inputStream.close();
        }
        // avro schema.
        Schema schema = new Schema.Parser().parse(schemaString);
        // generic record for page-view-event.
        GenericData.Record record = new GenericData.Record(schema);
        record.put("charging", batteryEvent.getCharging());
        record.put("charging_source", batteryEvent.getChargingSource());
        record.put("current_capacity", batteryEvent.getCurrentCapacity());
        record.put("moduleL_temp", batteryEvent.getModuleLTemp());
        record.put("moduleR_temp", batteryEvent.getModuleRTemp());
        record.put("processor1_temp", batteryEvent.getProcessor1Temp());
        record.put("processor2_temp", batteryEvent.getProcessor2Temp());
        record.put("processor3_temp", batteryEvent.getProcessor3Temp());
        record.put("processor4_temp", batteryEvent.getProcessor4Temp());
        record.put("inverter_state", batteryEvent.getInverterState());
        record.put("SoC_regulator", batteryEvent.getSoCRegulator());


        return record;
    }
}
