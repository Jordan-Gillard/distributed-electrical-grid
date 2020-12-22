package spring;

import avro.BatteryEvent;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EnergyResourcesController {
    @PostMapping("/event/{uuid}")
    public void handlePostEvent(
        @PathVariable("uuid") String uuid,
        BatteryEvent batteryEvent) {
        System.out.println(batteryEvent);
    }
}
