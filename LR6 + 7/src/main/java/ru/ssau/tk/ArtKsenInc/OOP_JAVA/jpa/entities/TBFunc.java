package ru.ssau.tk.ArtKsenInc.OOP_JAVA.jpa.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "TBFunc")
public class TBFunc {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Setter
    @Column(nullable = false)
    private double[] xValues;

    @Setter
    @Column(nullable = false)
    private double[] yValues;

    public TBFunc() {}

    public TBFunc(double[] xValues, double[] yValues) {
        this.xValues = xValues;
        this.yValues = yValues;
    }

}
