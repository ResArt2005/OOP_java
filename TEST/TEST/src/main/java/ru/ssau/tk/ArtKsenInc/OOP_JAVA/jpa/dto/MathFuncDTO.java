package ru.ssau.tk.ArtKsenInc.OOP_JAVA.jpa.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions.MathFunction;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MathFuncDTO {
    private Integer id;
    private String name;
    private MathFunction function;
}
