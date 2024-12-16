package ru.ssau.tk.ArtKsenInc.OOP_JAVA.jpa.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.jpa.entities.User;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Integer id;
    private String token;
    private String login;
    public UserDTO(User user){
        id = user.getId();
        token = user.getToken();
        login = user.getLogin();
    }
}
