package ru.ssau.tk.ArtKsenInc.OOP_JAVA.web.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogDTO {
    private int id;
    private String message;
    private Timestamp timestamp;
}