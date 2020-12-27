import org.apache.avro.generic.GenericRecord;
import org.junit.jupiter.api.Test;
import spring.EnergyResourcesController;

import java.io.IOException;

public class TestEnergyResourcesController {
    @Test
    public void testBuildRecordDoesNotThrowError() throws IOException {
        EnergyResourcesController.buildRecord();
    }
}
