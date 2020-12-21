package energy_resources;

import avro.BatteryEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EnergyResourcesController {

    @GetMapping("/event")
    public void handleEvent(BatteryEvent batteryEvent) {
        System.out.println(batteryEvent);
    }
}
