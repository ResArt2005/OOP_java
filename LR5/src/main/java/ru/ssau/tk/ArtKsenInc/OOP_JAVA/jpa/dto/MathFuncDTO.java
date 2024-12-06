package ru.ssau.tk.ArtKsenInc.OOP_JAVA.jpa.dto;

import ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions.MathFunction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MathFuncDTO {
    private Integer id;
    private String name;
    private MathFunction function;
}
