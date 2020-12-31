import avro.BatteryEvent;
import kafka.BatteryEventProducer;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class BatteryEventProducerTest {

    @Test
    public void testBuildRecordDoesNotThrowError() throws IOException {
        BatteryEvent batteryEvent = new BatteryEvent();
        BatteryEventProducer.buildRecord(batteryEvent);
    }
}
