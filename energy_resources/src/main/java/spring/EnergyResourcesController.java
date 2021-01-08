package spring;

import avro.BatteryEvent;
import kafka.BatteryEventProducer;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Date;

@RestController @RequestMapping(value = "/event")
public class EnergyResourcesController {
    private final Date date = new Date();

    @PostMapping("/{uuid}") BatteryEvent postBatteryEvent(
        @PathVariable("uuid") String uuid,
        @RequestBody BatteryEvent batteryEvent) throws IOException {
        batteryEvent.setTime(date.getTime());
        BatteryEventProducer.produce(batteryEvent);
        return batteryEvent;
    }
}
