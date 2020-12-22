package spring;

import avro.BatteryEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EnergyResourcesController {

    @PostMapping("/event")
    public void handlePostEvent(BatteryEvent batteryEvent) {
        System.out.println(batteryEvent);
    }

    @GetMapping("/event")
    public void handleGetEvent(BatteryEvent batteryEvent) {
        System.out.println(batteryEvent);
    }
}
