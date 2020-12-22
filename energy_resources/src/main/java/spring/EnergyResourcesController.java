package spring;

import avro.BatteryEvent;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class EnergyResourcesController {
    private final Date date = new Date();

    @PostMapping("/event/{uuid}")
    public void handlePostEvent(
        @PathVariable("uuid") String uuid,
        BatteryEvent batteryEvent) {
        batteryEvent.setTime(date.getTime());
        System.out.println(batteryEvent);
    }
}
