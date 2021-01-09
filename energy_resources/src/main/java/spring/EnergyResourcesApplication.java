package spring;

import avro.BatteryEvent;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class EnergyResourcesApplication {
    public static void main(String[] args) {
        SpringApplication.run(EnergyResourcesApplication.class, args);
    }


    @Bean
    BatteryEvent batteryEvent() {
        return new BatteryEvent();
    }
}
