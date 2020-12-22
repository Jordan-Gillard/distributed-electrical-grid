package src.main.java;

import avro.BatteryEvent;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EnergyResourcesController {

    @PostMapping("/event")
    public void handleEvent(BatteryEvent batteryEvent) {
        System.out.println(batteryEvent);
    }
}
