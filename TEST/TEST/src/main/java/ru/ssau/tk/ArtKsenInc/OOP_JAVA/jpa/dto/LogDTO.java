package ru.ssau.tk.ArtKsenInc.OOP_JAVA.jpa.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogDTO {
    private int id;
    private String message;
    private String timestamp;
}