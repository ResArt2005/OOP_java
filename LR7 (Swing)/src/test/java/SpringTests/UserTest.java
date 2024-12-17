package SpringTests;

import ru.ssau.tk.ArtKsenInc.OOP_JAVA.jpa.entities.User;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.Main;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = Main.class)
public class UserTest {

    @Test
    public void testNoArgsConstructor() {
        User user = new User();
        assertNotNull(user);
        assertNotNull(user.getToken());
        assertEquals("Unknown", user.getLogin());
        assertEquals("Unknown", user.getPassword());
    }

    @Test
    public void testAllArgsConstructor() {
        String login = "testLogin";
        String password = "testPassword";
        User user = new User(login, password);

        assertNotNull(user.getToken());
        assertEquals(login, user.getLogin());
        assertEquals(password, user.getPassword());
    }

    @Test
    public void testSettersAndGetters() {
        User user = new User();
        String token = UUID.randomUUID().toString();
        String login = "testLogin";
        String password = "testPassword";

        user.setToken(token);
        user.setLogin(login);
        user.setPassword(password);

        assertEquals(token, user.getToken());
        assertEquals(login, user.getLogin());
        assertEquals(password, user.getPassword());
    }
}
