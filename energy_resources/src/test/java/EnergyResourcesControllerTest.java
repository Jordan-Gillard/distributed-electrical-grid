import org.apache.avro.data.Json;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
public class EnergyResourcesControllerTest {
    @Test
    void testPostBatteryEventReturnsValidBatteryEvent(@Autowired
        WebTestClient client) throws IOException {
        String batteryEventJsonString = "{\n"
            + "  \"charging_source\": \"solar\",\n"
            + "  \"processor4_temp\": 79,\n"
            + "  \"device_id\": \"52a9351a-7030-434d-932c-dd0fe4d3b052\",\n"
            + "  \"processor2_temp\": 175,\n"
            + "  \"processor1_temp\": 136,\n"
            + "  \"charging\": -261,\n"
            + "  \"current_capacity\": 6329,\n"
            + "  \"inverter_state\": 6,\n"
            + "  \"moduleL_temp\": 82,\n"
            + "  \"moduleR_temp\": 179,\n"
            + "  \"processor3_temp\": 85,\n"
            + "  \"soC_regulator\": 28.63366\n"
            + "}\n";
//        assert client.post().bodyValue(batteryEventJsonString);
    }
}
