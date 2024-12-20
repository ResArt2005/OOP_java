package SpringTests;

import ru.ssau.tk.ArtKsenInc.OOP_JAVA.jpa.dto.UserDTO;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.Main;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = Main.class)

public class UserDTOTest {

    @Test
    public void testNoArgsConstructor() {
        UserDTO userDTO = new UserDTO();
        assertNotNull(userDTO);
    }

    @Test
    public void testAllArgsConstructor() {
        Integer id = 1;
        String token = "testToken";
        String login = "testLogin";
        UserDTO userDTO = new UserDTO(id, token, login);

        assertEquals(id, userDTO.getId());
        assertEquals(token, userDTO.getToken());
        assertEquals(login, userDTO.getLogin());
    }

    @Test
    public void testSettersAndGetters() {
        UserDTO userDTO = new UserDTO();
        Integer id = 1;
        String token = "testToken";
        String login = "testLogin";
        String password = "testPassword";

        userDTO.setId(id);
        userDTO.setToken(token);
        userDTO.setLogin(login);

        assertEquals(id, userDTO.getId());
        assertEquals(token, userDTO.getToken());
        assertEquals(login, userDTO.getLogin());
    }
}
