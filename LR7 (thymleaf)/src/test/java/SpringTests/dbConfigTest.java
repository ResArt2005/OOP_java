package SpringTests;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.Main;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = Main.class)
public class dbConfigTest {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Test
    void contextLoads() {
        // Проверяем, что бин создан и не равен null
        assertNotNull(namedParameterJdbcTemplate);
    }
}