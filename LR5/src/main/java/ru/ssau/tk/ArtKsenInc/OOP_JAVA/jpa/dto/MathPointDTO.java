package ru.ssau.tk.ArtKsenInc.OOP_JAVA.jpa.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MathPointDTO {
    private int id;
    private double x;
    private double y;
}