package spring;

import avro.BatteryEvent;

import kafka.BatteryEventProducer;
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


    @PostMapping("/event/{uuid}")
    public void handlePostEvent(
        @PathVariable("uuid") String uuid,
        BatteryEvent batteryEvent) throws IOException {
        // TODO: Figure out why battery event is all 0s
        batteryEvent.setTime(date.getTime());
        BatteryEventProducer.produce(batteryEvent);
    }
}
