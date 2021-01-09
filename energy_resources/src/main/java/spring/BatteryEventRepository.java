package spring;

import avro.BatteryEvent;
import org.springframework.data.repository.CrudRepository;

public interface BatteryEventRepository extends
    CrudRepository<BatteryEvent, String> {
}
