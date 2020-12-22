package spring;

import avro.BatteryEvent;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@RestController
public class EnergyResourcesController {
    Logger logger = Logger.getLogger("Spring Logger");


    // TODO: Events are posted to event/UUID. We need to match on UUID
    @PostMapping("/event")
    public void handlePostEvent(BatteryEvent batteryEvent) {
        logger.info("test");
        System.out.println(batteryEvent);
    }
}
