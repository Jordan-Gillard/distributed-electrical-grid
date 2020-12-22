import org.junit.Before;
import org.junit.Test;

public class EventGeneratorTest {
    private EventGenerator eventGenerator;


    @Before
    public void setUp() {
        this.eventGenerator = new EventGenerator();
    }


    @Test
    public void testNextEvent() throws Exception {
        eventGenerator.nextEvent();
    }
}
