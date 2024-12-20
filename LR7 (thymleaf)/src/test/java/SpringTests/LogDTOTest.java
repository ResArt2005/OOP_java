package SpringTests;

import org.springframework.boot.test.context.SpringBootTest;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.Main;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.jpa.dto.LogDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;

@SpringBootTest(classes = Main.class)

public class LogDTOTest {

    @Test
    public void testLogDTOConstructor() {
        int id = 1;
        String message = "Test message";
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LogDTO logDTO = new LogDTO(id, message, timestamp.toLocalDateTime().format(formatter));

        assertEquals(id, logDTO.getId());
        assertEquals(message, logDTO.getMessage());
        assertEquals(timestamp.toLocalDateTime().format(formatter), logDTO.getTimestamp());
    }

    @Test
    public void testLogDTOSettersAndGetters() {
        LogDTO logDTO = new LogDTO();
        int id = 1;
        String message = "Test message";
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        logDTO.setId(id);
        logDTO.setMessage(message);
        logDTO.setTimestamp(timestamp.toLocalDateTime().format(formatter));

        assertEquals(id, logDTO.getId());
        assertEquals(message, logDTO.getMessage());
        assertEquals(timestamp.toLocalDateTime().format(formatter), logDTO.getTimestamp());
    }
}
