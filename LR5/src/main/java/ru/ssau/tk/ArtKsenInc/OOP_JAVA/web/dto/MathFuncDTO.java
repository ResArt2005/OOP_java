package ru.ssau.tk.ArtKsenInc.OOP_JAVA.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MathFuncDTO {
    private int id;
    private double x;
    private double y;
    private long hash;
}