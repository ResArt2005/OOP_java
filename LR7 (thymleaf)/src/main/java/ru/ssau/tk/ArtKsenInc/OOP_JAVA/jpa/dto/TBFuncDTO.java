package ru.ssau.tk.ArtKsenInc.OOP_JAVA.jpa.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions.Point;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions.TabulatedFunction;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TBFuncDTO {
    private int id;
    private double[] xValues;
    private double[] yValues;
    private Point[] points;
    public TBFuncDTO(TabulatedFunction tbFunc){
        points = new Point[tbFunc.getCount()];
        int i = 0;
        for(Point point: tbFunc){
            points[i].x = point.x;
            points[i].y = point.y;
            ++i;
        }

    }
}