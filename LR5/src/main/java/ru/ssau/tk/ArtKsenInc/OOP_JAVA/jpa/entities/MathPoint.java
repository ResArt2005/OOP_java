package ru.ssau.tk.ArtKsenInc.OOP_JAVA.jpa.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "MathPoint")
public class MathPoint {
    // Геттеры и сеттеры
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Double x;

    @Column(nullable = false)
    private Double y;


    public MathPoint() {
    }

    public MathPoint(Double x, Double y) {
        this.x = x;
        this.y = y;
    }

}
