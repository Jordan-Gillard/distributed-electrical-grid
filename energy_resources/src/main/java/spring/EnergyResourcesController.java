package spring;

import avro.BatteryEvent;
import kafka.BatteryEventProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping(value = "/event")
public class EnergyResourcesController {
    @Autowired
    private final BatteryEventRepository batteryEventRepository;


    public EnergyResourcesController(BatteryEventRepository batteryEventRepository) {
        this.batteryEventRepository = batteryEventRepository;

        this.batteryEventRepository.save(new BatteryEvent());
    }


    @PostMapping("/{uuid}") BatteryEvent postBatteryEvent(
        @PathVariable("uuid") String uuid,
        @RequestBody BatteryEvent batteryEvent) throws IOException {
        BatteryEventProducer.produce(batteryEvent);
        return batteryEvent;
    }


    @GetMapping("/{uuid}")
    Optional<BatteryEvent> getBatteryEvent(
        @PathVariable("uuid") String uuid
    ) {
        return batteryEventRepository.findById(uuid);

    }
}
