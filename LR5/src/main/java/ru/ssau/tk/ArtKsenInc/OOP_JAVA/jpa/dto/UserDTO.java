package ru.ssau.tk.ArtKsenInc.OOP_JAVA.jpa.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Integer id;
    private String token;
    private String login;
    private String password;
}
