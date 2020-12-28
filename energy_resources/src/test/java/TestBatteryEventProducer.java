import avro.BatteryEvent;
import kafka.BatteryEventProducer;
import org.apache.avro.generic.GenericRecord;
import org.junit.jupiter.api.Test;
import spring.EnergyResourcesController;

import java.io.IOException;

public class TestBatteryEventProducer {


    @Test
    public void testBuildRecordDoesNotThrowError() throws IOException {
        BatteryEvent batteryEvent = new BatteryEvent();
        BatteryEventProducer.buildRecord(batteryEvent);
    }
}
