package SpringTests;

import org.springframework.boot.test.context.SpringBootTest;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.Main;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.jpa.entities.Log;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Timestamp;
@SpringBootTest(classes = Main.class)
public class LogTest {

    @Test
    public void testLogConstructor() {
        String message = "Test message";
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Log log = new Log(message, timestamp);

        assertEquals(message, log.getMessage());
        assertEquals(timestamp, log.getTimestamp());
    }

    @Test
    public void testLogSettersAndGetters() {
        Log log = new Log();
        String message = "Test message";
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        log.setMessage(message);
        log.setTimestamp(timestamp);

        assertEquals(message, log.getMessage());
        assertEquals(timestamp, log.getTimestamp());
    }
}
